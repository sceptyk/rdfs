package io.rdfs.helper;

import io.rdfs.model.DistributedFile;
import io.rdfs.model.Settings;

import java.util.List;

public interface IDataHelper {
    List<DistributedFile> getAllFiles();
    void updateFile(DistributedFile distributedFile);
    void updateAllFiles(List<DistributedFile> distributedFiles);

    Settings getSettings();
    void updateSettings(Settings config);

    void removeFile(DistributedFile distributedFile);
}
