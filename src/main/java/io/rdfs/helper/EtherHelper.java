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
    private String address = "001be8b0a3c6a011a07e1ab75401d0d7900d954e";

    private Gson gson = new Gson();
    private boolean acceptsFiles;
    private ConnectionReadySubscriber connectionReadySubscriber;

    private EtherHelper() {
        dataHelper = new DataHelper();
        web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));
    }

    public static EtherHelper getInstance() {
        if (instance == null) {
            instance = new EtherHelper();
        }

        return instance;
    }

    @Override
    public void connect(String password, ConnectionReadySubscriber subscriber) {
        try {
            Settings settings = dataHelper.getSettings();

            credentials = WalletUtils.loadCredentials(
                    settings.get(Settings.WALLET_PASSWORD),
                    settings.get(Settings.WALLET_FILE));

            connectionReadySubscriber = subscriber;
            ws = new WebSocketClientImpl(new URI("ws://achex.ca:4010"));
            ws.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        ws.close();
    }

    @Override
    public void publishOffer(DistributedFile distributedFile) throws Exception {

        FileHelper fileHelper = FileHelper.getInstance();
        List<byte[]> chunks = fileHelper.splitFile(distributedFile);

        for (int i = 0; i < chunks.size(); i++) {
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
                public void onSubscribe(Subscription s) {
                }

                @Override
                public void onNext(OfferContract.OfferAcceptedEventResponse offerAcceptedEventResponse) {
                    distributedFile.chunks.add(finalI, offerAcceptedEventResponse.file);
                    dataHelper.updateFile(distributedFile);
                }

                @Override
                public void onError(Throwable t) {
                }

                @Override
                public void onComplete() {
                }
            });

            dataHelper.updateFile(distributedFile);
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

            if (message == "{\"auth\":\"ok\"}") {
                connectionReadySubscriber.onReady();
            } else {
                WSObject wsObject = gson.fromJson(message, WSObject.class);
                if (wsObject.message == null) return;

                if (wsObject.message.type == WSMessageType.NEW_FILE) {
                    if (acceptsFiles) {
                        Offer offer = (Offer) wsObject.message.getContent(Offer.class);

                        OfferContract offerContract = OfferContract.load(offer.contract, web3j, credentials, new DefaultGasProvider());
                        offerContract.accept();

                        List<DistributedFile> distributedFiles = dataHelper.getAllFiles();

                        OfferContract.OfferAcceptedEventResponse offerAcceptedEventResponse = offerContract.getOfferAcceptedEvents(offerContract.getTransactionReceipt().get()).get(0);

                        DistributedFile newDistributedFile = new DistributedFile();
                        newDistributedFile.status = DistributedFile.Status.COLLECTABLE;
                        newDistributedFile.contract = offerAcceptedEventResponse.file;

                        distributedFiles.add(newDistributedFile);

                        FileChunkContract fileChunkContract = FileChunkContract.load(offerAcceptedEventResponse.file, web3j, credentials, new DefaultGasProvider());
                        fileChunkContract.downloadRequestEventFlowable(new EthFilter()).subscribe(new Subscriber<FileChunkContract.DownloadRequestEventResponse>() {
                            @Override
                            public void onSubscribe(Subscription s) {
                            }

                            @Override
                            public void onNext(FileChunkContract.DownloadRequestEventResponse downloadRequestEventResponse) {
                                String owner = downloadRequestEventResponse.owner;

                                DistributedFile foundDistributedFile = dataHelper.getAllFiles().stream().filter(file -> file.contract == offerAcceptedEventResponse.file).collect(Collectors.toList()).get(0);

                                FileHelper fileHelper = FileHelper.getInstance();
                                byte[] chunk = fileHelper.getFileAsChunk(foundDistributedFile);
                                fileChunkContract.share(chunk);
                            }

                            @Override
                            public void onError(Throwable t) {
                            }

                            @Override
                            public void onComplete() {
                            }
                        });

                        fileChunkContract.cancelSharingEventFlowable(new EthFilter()).subscribe(new Subscriber<FileChunkContract.CancelSharingEventResponse>() {
                            @Override
                            public void onSubscribe(Subscription s) {
                            }

                            @Override
                            public void onNext(FileChunkContract.CancelSharingEventResponse cancelSharingEventResponse) {
                                dataHelper.removeFile(newDistributedFile);
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
