package io.rdfs.helper;

import com.google.gson.Gson;
import io.rdfs.contract.FileChunkContract;
import io.rdfs.contract.OfferContract;
import io.rdfs.model.*;
import io.reactivex.subscribers.SafeSubscriber;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EtherHelper implements IEtherHelper {

    private static EtherHelper instance;
    private Web3j web3j;
    private WebSocketClient ws;
    private Credentials credentials;
    private String address = "001be8b0a3c6a011a07e1ab75401d0d7900d954e";

    private Gson gson = new Gson();
    private boolean acceptsFiles;

    private EtherHelper() throws IOException, CipherException, URISyntaxException {
        web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));

        credentials = WalletUtils.loadCredentials(
                "distributedsystems",
                "C:\\Users\\c2h6o\\AppData\\Roaming\\Ethereum\\testnet\\keystore\\UTC--2019-01-03T19-28-46.508000000Z--001be8b0a3c6a011a07e1ab75401d0d7900d954e.json");
    }

    public static EtherHelper getInstance() {
        if (instance == null) {
            try {
                instance = new EtherHelper();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CipherException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    @Override
    public void connect() {
        try {
            ws = new WebSocketClientImpl(new URI("ws://achex.ca:4010"));
            ws.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        ws.close();
    }

    @Override
    public void publishOffer(File file) throws Exception {

        FileHelper fileHelper = FileHelper.getInstance();
        List<byte[]> chunks = fileHelper.splitFile(file);

        for (int i=0;i<chunks.size();i++) {
            byte[] chunk = chunks.get(i);
            OfferContract offerContract = OfferContract
                    .deploy(
                            web3j,
                            credentials,
                            new DefaultGasProvider(),
                            chunk
                    )
                    .send();

            ws.send(WSObject.createMessage(new WSMessage(WSMessageType.NEW_FILE, offerContract.getContractAddress())));

            int finalI = i;
            offerContract.offerAcceptedEventFlowable(new EthFilter()).safeSubscribe(new Subscriber<OfferContract.OfferAcceptedEventResponse>() {
                @Override
                public void onSubscribe(Subscription s) {}

                @Override
                public void onNext(OfferContract.OfferAcceptedEventResponse offerAcceptedEventResponse) {
                    file.chunks.add(finalI, offerAcceptedEventResponse.file);
                    DataHelper dataHelper = DataHelper.getInstance();
                    dataHelper.updateFile(file);
                }

                @Override
                public void onError(Throwable t) {}

                @Override
                public void onComplete() {}
            });

            DataHelper dataHelper = DataHelper.getInstance();
            dataHelper.updateFile(file);
        }

        System.out.println("Offer sent");
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
    public void requestFile(File file) {
        List<byte[]> fileChunks = new ArrayList<>();
        for (int i = 0; i < file.chunks.size(); i++) {
            String chunkContractAddress = file.chunks.get(i);
            FileChunkContract fileChunkContract =
                    FileChunkContract.load(chunkContractAddress, web3j, credentials, new DefaultGasProvider());
            fileChunkContract.request();
            FileChunkContract.DownloadResponseEventResponse downloadResponseEventResponse =
                    fileChunkContract.getDownloadResponseEvents(fileChunkContract.getTransactionReceipt().get()).get(0);
            fileChunks.add(i, downloadResponseEventResponse.fileChunk);
        }

        FileHelper fileHelper = FileHelper.getInstance();
        fileHelper.glueFile(fileChunks);
    }

    @Override
    public void deleteFile(File file) {
        for (String chunkContractAddress : file.chunks) {
            FileChunkContract fileChunkContract =
                    FileChunkContract.load(chunkContractAddress, web3j, credentials, new DefaultGasProvider());
            fileChunkContract.cancel();
        }

        DataHelper dataHelper = DataHelper.getInstance();
        dataHelper.removeFile(file);
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

            if (message == "{\"auth\":\"ok\"}") {
                //TODO allow sending
            } else {
                WSObject wsObject = gson.fromJson(message, WSObject.class);
                if (wsObject.message == null) return;

                if (wsObject.message.type == WSMessageType.NEW_FILE) {
                    if (acceptsFiles) {
                        Offer offer = (Offer) wsObject.message.getContent(Offer.class);

                        OfferContract offerContract = OfferContract.load(offer.contract, web3j, credentials, new DefaultGasProvider());
                        offerContract.accept();

                        DataHelper dataHelper = DataHelper.getInstance();
                        List<File> files = dataHelper.getAllFiles();

                        OfferContract.OfferAcceptedEventResponse offerAcceptedEventResponse = offerContract.getOfferAcceptedEvents(offerContract.getTransactionReceipt().get()).get(0);

                        File newFile = new File();
                        newFile.status = File.Status.COLLECTABLE;
                        newFile.contract = offerAcceptedEventResponse.file;

                        files.add(newFile);

                        FileChunkContract fileChunkContract = FileChunkContract.load(offerAcceptedEventResponse.file, web3j, credentials, new DefaultGasProvider());
                        fileChunkContract.downloadRequestEventFlowable(new EthFilter()).subscribe(new Subscriber<FileChunkContract.DownloadRequestEventResponse>() {
                            @Override
                            public void onSubscribe(Subscription s) {
                            }

                            @Override
                            public void onNext(FileChunkContract.DownloadRequestEventResponse downloadRequestEventResponse) {
                                String owner = downloadRequestEventResponse.owner;

                                File foundFile = dataHelper.getAllFiles().stream().filter(file -> file.contract == offerAcceptedEventResponse.file).collect(Collectors.toList()).get(0);

                                FileHelper fileHelper = FileHelper.getInstance();
                                byte[] chunk = fileHelper.getFileAsChunk(foundFile);
                                fileChunkContract.share(chunk);
                            }

                            @Override
                            public void onError(Throwable t) {
                            }

                            @Override
                            public void onComplete() {
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
