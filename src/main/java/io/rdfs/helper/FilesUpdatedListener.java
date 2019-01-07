package io.rdfs.helper;

import io.rdfs.model.DistributedFile;

import java.util.List;

public interface FilesUpdatedListener {
    void onChange(List<DistributedFile> distributedFileList);
}
