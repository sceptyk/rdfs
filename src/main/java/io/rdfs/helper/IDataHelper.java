package io.rdfs.helper;

import io.rdfs.model.DistributedFile;
import io.rdfs.model.Settings;

import java.util.List;

public interface IDataHelper {
    List<DistributedFile> getAllFiles();
    void updateFile(DistributedFile distributedFile);
    void updateAllFiles(List<DistributedFile> distributedFiles);
    void removeFile(DistributedFile distributedFile);
    void subscribeToFilesChanges(FilesUpdatedListener filesUpdatedListener);

    Settings getSettings();
    void updateSettings(Settings config);
}
