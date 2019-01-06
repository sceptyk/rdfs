package io.rdfs.helper;

import io.rdfs.model.DistributedFile;

import javax.crypto.SecretKey;
import java.util.List;

public interface IFileHelper {
    List<byte[]> splitFile(DistributedFile distributedFile);
    void glueFile(List<byte[]> fileChunks, SecretKey key);

    byte[] getFileAsChunk(DistributedFile foundDistributedFile);
}
