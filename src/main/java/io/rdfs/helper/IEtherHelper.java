package io.rdfs.helper;

import io.rdfs.model.DistributedFile;
import io.rdfs.model.Offer;

public interface IEtherHelper {

    void publishOffer(Offer offer);
    void handleResponse(Offer offer);
    void acceptOffer(Offer offer, DistributedFile file);
    void requestFile(DistributedFile file);
}
