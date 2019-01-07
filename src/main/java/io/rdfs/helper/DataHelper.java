package io.rdfs.helper;

import io.rdfs.model.DistributedFile;
import io.rdfs.model.Settings;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataHelper implements IDataHelper {

    private String filesPath;
    private String settingsPath;
    private static DataHelper instance;
    private FilesUpdatedListener filesUpdatedListener;

    private DataHelper() {
        String dir = getClass().getResource("/../resources/store").getPath();
        filesPath = dir + "/filesStorage.ser";
        settingsPath = dir + "/settingsStorage.ser";
    }
    
    public static DataHelper getInstance(){
        if(instance == null){
            instance = new DataHelper();
        }
        
        return instance;
    }

    @Override
    public List<DistributedFile> getAllFiles() {
        List<DistributedFile> distributedFiles = new ArrayList<>();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filesPath));
            distributedFiles = (List<DistributedFile>) ois.readObject();
        } catch (EOFException e) {
            //Do nothing
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
        for (int i = 0; i < distributedFiles.size(); i++) {
            DistributedFile oldDistributedFile = distributedFiles.get(i);
            if (oldDistributedFile.contract == distributedFile.contract) {
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

            if(filesUpdatedListener != null){
                filesUpdatedListener.onChange(distributedFiles);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFile(DistributedFile distributedFile) {
        List<DistributedFile> distributedFiles = getAllFiles();
        for (int i = 0; i < distributedFiles.size(); i++) {
            DistributedFile oldDistributedFile = distributedFiles.get(i);
            if (oldDistributedFile.contract == distributedFile.contract) {
                distributedFiles.remove(i);
                break;
            }
        }
        updateAllFiles(distributedFiles);
    }

    @Override
    public void subscribeToFilesChanges(FilesUpdatedListener filesUpdatedListener) {
        this.filesUpdatedListener = filesUpdatedListener;
    }

    @Override
    public Settings getSettings() {
        Settings settings = new Settings();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(settingsPath));
            settings = (Settings) ois.readObject();
        } catch (EOFException e) {
            //Do nothing
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
