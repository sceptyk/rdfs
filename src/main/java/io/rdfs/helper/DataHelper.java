package io.rdfs.helper;

import io.rdfs.model.DistributedFile;
import io.rdfs.model.Settings;

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
    public List<DistributedFile> getAllFiles() {
        List<DistributedFile> distributedFiles = new ArrayList<>();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filesPath));
            distributedFiles = (List<DistributedFile>)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return distributedFiles;
    }

    @Override
    public void updateFile(DistributedFile distributedFile) {
        List<DistributedFile> distributedFiles = getAllFiles();
        for(int i = 0; i< distributedFiles.size(); i++){
            DistributedFile oldDistributedFile = distributedFiles.get(i);
            if(oldDistributedFile.contract == distributedFile.contract){
                distributedFiles.set(i, distributedFile);
                break;
            }
        }
        updateAllFiles(distributedFiles);
    }

    @Override
    public void updateAllFiles(List<DistributedFile> distributedFiles) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filesPath));
            oos.writeObject(distributedFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFile(DistributedFile distributedFile) {
        List<DistributedFile> distributedFiles = getAllFiles();
        for(int i = 0; i< distributedFiles.size(); i++){
            DistributedFile oldDistributedFile = distributedFiles.get(i);
            if(oldDistributedFile.contract == distributedFile.contract){
                distributedFiles.remove(i);
                break;
            }
        }
        updateAllFiles(distributedFiles);
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
