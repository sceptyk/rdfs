package io.rdfs.helper;

import io.rdfs.model.File;
import io.rdfs.model.Settings;

import java.util.List;

public interface IDataHelper {
    List<File> getAllFiles();
    void updateFile(File file);
    void updateAllFiles(List<File> files);

    Settings getSettings();
    void updateSettings(Settings config);
}
