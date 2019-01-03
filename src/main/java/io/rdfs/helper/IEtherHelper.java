package io.rdfs.helper;

import io.rdfs.model.File;
import io.rdfs.model.Offer;

public interface IEtherHelper {

    void publishOffer(Offer offer);
    void handleResponse(Offer offer);
    void acceptOffer(Offer offer, File file);
    void requestFile(File file);
}
