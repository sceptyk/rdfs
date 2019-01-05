package helper;

import io.rdfs.model.DistributedFile;
import io.rdfs.model.Settings;

import java.util.List;

public interface IDataHelper {
    List<DistributedFile> getAllFiles();
    void updateFile(DistributedFile file);
    void updateAllFiles(List<DistributedFile> files);
    Settings getSettings();
    void updateSettings(Settings config);
}
