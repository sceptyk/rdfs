package io.rdfs.helper;

import io.rdfs.model.File;
import io.rdfs.model.Offer;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;

public class EtherHelper implements IEtherHelper {

    private static EtherHelper instance;
    private static Web3j web3j;
    private static Credentials credentials;

    private EtherHelper() throws IOException, CipherException {
        web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/af915edd6aaa4870ab66d2aefba076c7"));
        credentials = WalletUtils.loadCredentials(
                "distributedsystems",
                "C:\\Users\\c2h6o\\AppData\\Roaming\\Ethereum\\testnet\\keystore\\UTC--2019-01-03T19-28-46.508000000Z--001be8b0a3c6a011a07e1ab75401d0d7900d954e.json");
    }

    public static EtherHelper getInstance(){
        if(instance == null) {
            try {
                instance = new EtherHelper();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CipherException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    @Override
    public void publishOffer(Offer offer) throws Exception {
        io.rdfs.contract.Offer offerContract = io.rdfs.contract.Offer
            .deploy(
                web3j,
                credentials,
                new DefaultGasProvider(),
                offer.chunk
            )
            .send();
    }

    @Override
    public void handleResponse(Offer offer) {

    }

    @Override
    public void acceptOffer(Offer offer, File file) {
        Web3j web3 = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/af915edd6aaa4870ab66d2aefba076c7"));
        web3.web3ClientVersion().flowable().subscribe(x -> {
            String clientVersion = x.getWeb3ClientVersion();

        });
    }

    @Override
    public void requestFile(File file) {

    }
}
