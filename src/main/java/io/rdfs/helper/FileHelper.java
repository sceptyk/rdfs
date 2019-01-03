package io.rdfs.helper;

import io.rdfs.model.File;

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
    public void splitFile(File file) {

    }
}
