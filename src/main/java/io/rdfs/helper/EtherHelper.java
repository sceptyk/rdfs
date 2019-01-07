package io.rdfs.helper;

import com.google.gson.Gson;
import io.rdfs.contract.FileChunkContract;
import io.rdfs.contract.OfferContract;
import io.rdfs.model.*;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EtherHelper implements IEtherHelper {

    private static EtherHelper instance;
    private final DataHelper dataHelper;
    private Web3j web3j;
    private WebSocketClient ws;
    private Credentials credentials;
    private String address;

    private Gson gson = new Gson();
    private boolean acceptsFiles;
    private ConnectionReadySubscriber connectionReadySubscriber;

    private EtherHelper() {
        dataHelper = DataHelper.getInstance();
        web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));
    }

    public static EtherHelper getInstance() {
        if (instance == null) {
            instance = new EtherHelper();
        }

        return instance;
    }

    @Override
    public ConnectionReadySubscriber connect(String password, String address, String wallet) {
        try {
            this.address = address;
            connectionReadySubscriber = new ConnectionReadySubscriber();

            credentials = WalletUtils.loadCredentials(password, wallet);

            WSObject.SENDER = address;
            ws = new WebSocketClientImpl(new URI("ws://achex.ca:4010"));
            ws.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connectionReadySubscriber;
    }

    @Override
    public void disconnect() {
        ws.close();
    }

    @Override
    public void publishOffer(DistributedFile distributedFile){

        FileHelper fileHelper = FileHelper.getInstance();
        List<byte[]> chunks = fileHelper.splitFile(distributedFile);

        dataHelper.updateFile(distributedFile);

        for (int i = 0; i < chunks.size(); i++) {

            int finalI = i;
            final int[] countAccepted = {0}; //multi thread integer
            int countSend = chunks.size();
            new Thread(() -> {
                byte[] chunk = chunks.get(finalI);
                OfferContract offerContract = null;
                try {
                    offerContract = OfferContract
                            .deploy(
                                    web3j,
                                    credentials,
                                    new DefaultGasProvider(),
                                    chunk
                            )
                            .send();

                    ws.send(WSObject.createMessage(new WSMessage(WSMessageType.NEW_FILE, offerContract.getContractAddress())));

                    System.out.println("Offer sent");

                    offerContract.offerAcceptedEventFlowable(new EthFilter()).safeSubscribe(new EthNextEventListener<OfferContract.OfferAcceptedEventResponse>() {
                        @Override
                        public void onNext(OfferContract.OfferAcceptedEventResponse offerAcceptedEventResponse) {
                            distributedFile.chunks.add(finalI, offerAcceptedEventResponse.file);
                            countAccepted[0]++;
                            if(countAccepted[0] == countSend)
                                distributedFile.status = DistributedFile.Status.UPLOADED;
                            dataHelper.updateFile(distributedFile);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    distributedFile.status = DistributedFile.Status.FAILED;
                    dataHelper.updateFile(distributedFile);
                }
            }).start();

        }
    }

    @Override
    public void subscribeToOffers() {
        acceptsFiles = true;
    }

    @Override
    public void unsubscribeToOffers() {
        acceptsFiles = false;
    }

    @Override
    public void requestFile(DistributedFile distributedFile) {
        List<byte[]> fileChunks = new ArrayList<>();
        for (int i = 0; i < distributedFile.chunks.size(); i++) {
            String chunkContractAddress = distributedFile.chunks.get(i);
            FileChunkContract fileChunkContract =
                    FileChunkContract.load(chunkContractAddress, web3j, credentials, new DefaultGasProvider());
            fileChunkContract.request();
            FileChunkContract.DownloadResponseEventResponse downloadResponseEventResponse =
                    fileChunkContract.getDownloadResponseEvents(fileChunkContract.getTransactionReceipt().get()).get(0);
            fileChunks.add(i, downloadResponseEventResponse.fileChunk);
        }

        FileHelper fileHelper = FileHelper.getInstance();
        fileHelper.glueFile(fileChunks, distributedFile.key);
    }

    @Override
    public void deleteFile(DistributedFile distributedFile) {
        if(distributedFile.chunks != null)
        for (String chunkContractAddress : distributedFile.chunks) {
            FileChunkContract fileChunkContract =
                    FileChunkContract.load(chunkContractAddress, web3j, credentials, new DefaultGasProvider());
            fileChunkContract.cancel();
        }

        dataHelper.removeFile(distributedFile);
    }

    private class WebSocketClientImpl extends WebSocketClient {

        public WebSocketClientImpl(URI serverUri) {
            super(serverUri);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            ws.send(WSObject.createInitMessage());
        }

        @Override
        public void onMessage(String message) {

            System.out.println(message);

            if (message.equals("{\"auth\":\"ok\"}")) {
                connectionReadySubscriber.ready();
            } else {
                WSObject wsObject = gson.fromJson(message, WSObject.class);
                if (wsObject.message == null) return;
                //if (wsObject.sender == address) return;

                if (wsObject.message.type == WSMessageType.NEW_FILE) {
                    if (acceptsFiles) {
                        String chunkContract = (String) wsObject.message.getContent(String.class);

                        OfferContract offerContract = OfferContract.load(chunkContract, web3j, credentials, new DefaultGasProvider());
                        offerContract.accept();

                        List<DistributedFile> distributedFiles = dataHelper.getAllFiles();

                        OfferContract.OfferAcceptedEventResponse offerAcceptedEventResponse = offerContract.getOfferAcceptedEvents(offerContract.getTransactionReceipt().get()).get(0);

                        FileHelper fileHelper = FileHelper.getInstance();
                        DistributedFile newDistributedFile = fileHelper.saveFileChunk(offerAcceptedEventResponse.fileChunk, offerAcceptedEventResponse.file);

                        distributedFiles.add(newDistributedFile);

                        FileChunkContract fileChunkContract = FileChunkContract.load(offerAcceptedEventResponse.file, web3j, credentials, new DefaultGasProvider());
                        fileChunkContract.downloadRequestEventFlowable(new EthFilter()).subscribe(new EthNextEventListener<FileChunkContract.DownloadRequestEventResponse>() {
                            @Override
                            public void onNext(FileChunkContract.DownloadRequestEventResponse downloadRequestEventResponse) {
                                DistributedFile foundDistributedFile = dataHelper.getAllFiles().stream().filter(file -> file.contract == offerAcceptedEventResponse.file).collect(Collectors.toList()).get(0);

                                FileHelper fileHelper = FileHelper.getInstance();
                                byte[] chunk = fileHelper.getFileAsChunk(foundDistributedFile);
                                fileChunkContract.share(chunk);
                            }
                        });

                        fileChunkContract.cancelSharingEventFlowable(new EthFilter()).subscribe(new EthNextEventListener<FileChunkContract.CancelSharingEventResponse>() {

                            @Override
                            public void onNext(FileChunkContract.CancelSharingEventResponse cancelSharingEventResponse) {
                                dataHelper.removeFile(newDistributedFile);
                            }
                        });
                    }
                }
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
        }

        @Override
        public void onError(Exception ex) {
            ex.printStackTrace();
        }
    }
}
