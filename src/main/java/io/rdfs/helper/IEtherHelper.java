package io.rdfs.helper;

import io.rdfs.model.File;
import io.rdfs.model.Offer;
import org.web3j.crypto.CipherException;

import java.io.IOException;

public interface IEtherHelper {

    void publishOffer(File file) throws Exception;
    void subscribeToOffers();
    void requestFile(File file);
}
