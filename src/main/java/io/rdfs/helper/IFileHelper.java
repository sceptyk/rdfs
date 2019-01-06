package io.rdfs.helper;

import io.rdfs.model.File;
import io.rdfs.model.Offer;

import javax.crypto.SecretKey;
import java.util.List;

public interface IFileHelper {
    List<byte[]> splitFile(File file);
    void glueFile(List<byte[]> fileChunks, SecretKey key);

    byte[] getFileAsChunk(File foundFile);
}
