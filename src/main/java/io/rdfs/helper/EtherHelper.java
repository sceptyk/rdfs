package io.rdfs.helper;

import io.rdfs.model.DistributedFile;
import io.rdfs.model.Offer;

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
    public void acceptOffer(Offer offer, DistributedFile file) {

    }

    @Override
    public void requestFile(DistributedFile file) {

    }
}
