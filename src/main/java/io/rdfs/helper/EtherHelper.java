package io.rdfs.helper;

import com.google.gson.Gson;
import io.rdfs.contract.OfferContract;
import io.rdfs.model.*;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class EtherHelper implements IEtherHelper {

    private static EtherHelper instance;
    private Web3j web3j;
    private WebSocketClient ws;
    private Credentials credentials;
    private String address = "001be8b0a3c6a011a07e1ab75401d0d7900d954e";

    private Gson gson = new Gson();
    private boolean acceptsFiles;

    private EtherHelper() throws IOException, CipherException, URISyntaxException {
        web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/af915edd6aaa4870ab66d2aefba076c7"));

        ws = new WebSocketClientImpl(new URI("ws://achex.ca:4010"));

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
        ws.connect();
    }

    @Override
    public void publishOffer(File file) throws Exception {

        FileHelper fileHelper = FileHelper.getInstance();
        List<Offer> offers = fileHelper.splitFile(file);

        for (Offer offer : offers) {
            OfferContract offerContract = OfferContract
                    .deploy(
                            web3j,
                            credentials,
                            new DefaultGasProvider(),
                            offer.chunk
                    )
                    .send();

            offer.owner = address;
            offer.contract = offerContract.getContractAddress();
            ws.send(WSObject.createMessage(new WSMessage(WSMessageType.NEW_FILE, offer)));

            DataHelper dataHelper = DataHelper.getInstance();
            dataHelper.updateFile(file);
        }

        System.out.println("Offer sent");
    }

    @Override
    public boolean switchFileSubscription() {
        return acceptsFiles = !acceptsFiles;
    }

    @Override
    public void requestFile(File file) {

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

            if(message == "{\"auth\":\"ok\"}"){
                //TODO allow sending
            } else{
                WSObject wsObject = gson.fromJson(message, WSObject.class);
                if(wsObject.message == null) return;

                if(wsObject.message.type == WSMessageType.NEW_FILE){
                    if(acceptsFiles) {
                        Offer offer = (Offer) wsObject.message.getContent(Offer.class);

                        OfferContract offerContract = OfferContract.load(offer.contract, web3j, credentials, new DefaultGasProvider());
                        RemoteCall<TransactionReceipt> response = offerContract.accept();
                        System.out.println(gson.toJson(response));

                        offer.responder = address;
                        ws.send(WSObject.createMessage(new WSMessage(WSMessageType.ACCEPTED, offer)));
                    }
                } else if(wsObject.message.type == WSMessageType.ACCEPTED){
                    //TODO update offer
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
