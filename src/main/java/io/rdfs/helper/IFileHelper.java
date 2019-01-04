package io.rdfs.helper;

import io.rdfs.model.File;
import io.rdfs.model.Offer;

import java.util.List;

public interface IFileHelper {
    List<Offer> splitFile(File file);
}
