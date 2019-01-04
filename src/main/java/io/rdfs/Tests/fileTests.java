package io.rdfs.Tests;
import io.rdfs.helper.DataHelper;
import io.rdfs.model.DistributedFile;
import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class fileTests extends TestCase {

    @Test
    public void testUpdateAllFilesAndGetAllFiles(){
        DataHelper data = new DataHelper();
        List<DistributedFile> files = new ArrayList<>();

        DistributedFile file1 = new DistributedFile();
        file1.name = "File 1";
        file1.owner = "Owner 1";
        file1.status = "Status 1";
        file1.path = "Path 1";

        DistributedFile file2 = new DistributedFile();
        file2.name = "File 1";
        file2.owner = "Owner 2";
        file2.status = "Status 2";
        file2.path = "Path 2";

        DistributedFile file3 = new DistributedFile();
        file3.name = "File 3";
        file3.owner = "Owner 3";
        file3.status = "Status 3";
        file3.path = "Path 3";

        files.add(file1);
        files.add(file2);
        files.add(file3);

        data.updateAllFiles(files);
        assertEquals(data.getAllFiles().get(0).name, "File 1");
        assertEquals(data.getAllFiles().get(1).status, "Status 2");
        assertEquals(data.getAllFiles().get(2).owner, "Owner 3");
    }

    @Test
    public void testUpdateFile(){
        DataHelper data = new DataHelper();
        List<DistributedFile> files = new ArrayList<>();

        DistributedFile file1 = new DistributedFile();
        file1.name = "File 1";
        file1.owner = "Owner 1";
        file1.status = "Status 1";
        file1.path = "Path 1";

        DistributedFile file2 = new DistributedFile();
        file2.name = "File 1";
        file2.owner = "Owner 2";
        file2.status = "Status 2";
        file2.path = "Path 2";

        files.add(file1);
        data.updateAllFiles(files);

        data.updateFile(file2);

        assertEquals(data.getAllFiles().get(0).name,"File 1");
        assertEquals(data.getAllFiles().get(0).owner,"Owner 2");
        assertEquals(data.getAllFiles().get(0).status,"Status 2");
        assertEquals(data.getAllFiles().get(0).path,"Path 2");
    }


}
