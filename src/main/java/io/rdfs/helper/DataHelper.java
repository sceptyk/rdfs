package io.rdfs.helper;

import io.rdfs.model.File;
import io.rdfs.model.Settings;

import javax.xml.crypto.Data;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataHelper implements IDataHelper {

    private String filesPath;
    private String settingsPath;

    public DataHelper(){
        String dir = DataHelper.class.getResource("/../resources/store/").getPath();
        filesPath = Paths.get(dir, "filesStorage.ser").toString();
        settingsPath = Paths.get(dir, "settingsStorage.ser").toString();
    }

    @Override
    public List<File> getAllFiles() {
        List<File> files = new ArrayList<>();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filesPath));
            files = (List<File>)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return files;
    }

    @Override
    public void updateFile(File file) {
        List<File> files = getAllFiles();
        for(int i=0;i<files.size();i++){
            File oldFile = files.get(i);
            if(oldFile.contract == file.contract){
                files.set(i, file);
                break;
            }
        }
        updateAllFiles(files);
    }

    @Override
    public void updateAllFiles(List<File> files) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filesPath));
            oos.writeObject(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFile(File file) {
        List<File> files = getAllFiles();
        for(int i=0;i<files.size();i++){
            File oldFile = files.get(i);
            if(oldFile.contract == file.contract){
                files.remove(i);
                break;
            }
        }
        updateAllFiles(files);
    }

    @Override
    public Settings getSettings() {
        Settings settings = new Settings();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(settingsPath));
            settings = (Settings)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return settings;
    }

    @Override
    public void updateSettings(Settings config) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(settingsPath));
            oos.writeObject(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
