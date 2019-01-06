package io.rdfs.model;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.List;

public class File implements Serializable {
    public String name;
    public String status;
    public String path;
    public String contract;
    public List<String> chunks;
    public SecretKey key;

    public class Status {
        public static final String SELECTED = "SELECTED";
        public static final String CHOPPED = "CHOPPED";
        public static final String DISTRIBUTED = "DISTRIBUTED";
        public static final String RECEIVED = "RECEIVED";
        public static final String PATCHED = "PATCHED";
        public static final String REMOVED = "REMOVED";
        public static final String COLLECTABLE = "COLLECTABLE";
    }
}
