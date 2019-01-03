package io.rdfs.helper;

import io.rdfs.model.File;
import io.rdfs.model.Settings;

import java.util.ArrayList;
import java.util.List;

public class DataHelper implements IDataHelper {

    private static DataHelper instance;

    private DataHelper(){}

    public static DataHelper getInstance(){
        if(instance == null) {
            instance = new DataHelper();
        }

        return instance;
    }

    @Override
    public List<File> getAllFiles() {
        //TODO load from file

        List<File> files = new ArrayList<>();
        File file = new File();
        file.status = "STATUS1";
        file.name = "NAME1";

        files.add(file);

        file = new File();
        file.status = "STATUS2";
        file.name = "NAME2";

        files.add(file);

        return files;
    }

    @Override
    public void updateFile(File file) {

    }

    @Override
    public void updateAllFiles(List<File> files) {

    }

    @Override
    public Settings getSettings() {
        return null;
    }

    @Override
    public void updateSettings(Settings config) {

    }
}
