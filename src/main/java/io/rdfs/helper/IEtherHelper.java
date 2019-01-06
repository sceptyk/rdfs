package io.rdfs.helper;

import io.rdfs.model.DistributedFile;

public interface IEtherHelper {
    void connect(String password, ConnectionReadySubscriber connectionReadySubscriber);
    void disconnect();
    void publishOffer(DistributedFile distributedFile) throws Exception;
    void subscribeToOffers();
    void unsubscribeToOffers();
    void requestFile(DistributedFile distributedFile);
    void deleteFile(DistributedFile distributedFile);
}
