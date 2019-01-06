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
    public List<byte[]> splitFile(File file) {
        return null;
    }

    @Override
    public void glueFile(List<byte[]> fileChunks) {

    }

    @Override
    public byte[] getFileAsChunk(File foundFile) {
        return new byte[0];
    }
}
