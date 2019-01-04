package io.rdfs.helper;

import io.rdfs.model.File;
import io.rdfs.model.Offer;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class EtherHelper implements IEtherHelper {

    private static EtherHelper instance;

    private EtherHelper(){}

    public static EtherHelper getInstance(){
        if(instance == null) {
            instance = new EtherHelper();
        }

        return instance;
    }

    @Override
    public void publishOffer(Offer offer) {

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
