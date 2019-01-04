package io.rdfs.helper;

import io.rdfs.model.File;

public interface IEtherHelper {
    void connect();
    void publishOffer(File file) throws Exception;
    boolean switchFileSubscription();
    void requestFile(File file);
}
