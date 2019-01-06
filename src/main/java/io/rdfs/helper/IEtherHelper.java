package io.rdfs.helper;

import io.rdfs.model.File;

public interface IEtherHelper {
    void connect();
    void disconnect();
    void publishOffer(File file) throws Exception;
    void subscribeToOffers();
    void unsubscribeToOffers();
    void requestFile(File file);
    void deleteFile(File file);
}
