package io.rdfs.helper;

import io.rdfs.model.File;
import io.rdfs.model.Offer;
import org.web3j.crypto.CipherException;

import java.io.IOException;

public interface IEtherHelper {

    void publishOffer(Offer offer) throws Exception;
    void handleResponse(Offer offer);
    void acceptOffer(Offer offer, File file);
    void requestFile(File file);
}
