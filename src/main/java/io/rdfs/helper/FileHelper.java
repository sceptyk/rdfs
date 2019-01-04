package io.rdfs.helper;

import io.rdfs.model.File;
import io.rdfs.model.Offer;

import java.util.ArrayList;
import java.util.List;

public class FileHelper implements IFileHelper {

    private static FileHelper instance;

    private FileHelper(){}

    public static FileHelper getInstance(){
        if(instance == null) {
            instance = new FileHelper();
        }

        return instance;
    }

    @Override
    public List<Offer> splitFile(File file) {
        List<Offer> offers = new ArrayList<>();

        Offer offer = new Offer();
        offer.chunk = new byte[]{0, 1, 2};
        offers.add(offer);

        return offers;
    }
}
