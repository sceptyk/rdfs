package io.rdfs.model;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.List;

public class DistributedFile implements Serializable {
    public String name;
    public String status;
    public String path;
    public String contract;
    public List<String> chunks;
    public SecretKey key;

    public class Status {
        public static final String UPLOADING = "UPLOADING";
        public static final String COLLECTABLE = "COLLECTABLE";
        public static final String UPLOADED = "UPLOADED";
        public static final String FAILED = "FAILED";
    }
}
