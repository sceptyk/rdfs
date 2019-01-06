package io.rdfs.helper;

import io.rdfs.model.File;
import io.rdfs.model.Offer;

import java.util.List;

public interface IFileHelper {
    List<byte[]> splitFile(File file);
    void glueFile(List<byte[]> fileChunks);

    byte[] getFileAsChunk(File foundFile);
}
