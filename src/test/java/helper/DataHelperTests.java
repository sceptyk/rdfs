package helper;
import io.rdfs.model.DistributedFile;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class DataHelperTests {

    private DataHelper dataHelper;

    @Before
    public void setUp(){
        String path = DataHelperTests.class.getResource("/../resources/store/fileStorage.xml").getPath();
        dataHelper = DataHelper.getInstance(path);
    }

    @Test
    public void testUpdateAllFilesAndGetAllFiles(){
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

        dataHelper.updateAllFiles(files);
        assertEquals(dataHelper.getAllFiles().get(0).name, "File 1");
        assertEquals(dataHelper.getAllFiles().get(1).status, "Status 2");
        assertEquals(dataHelper.getAllFiles().get(2).owner, "Owner 3");
    }

    @Test
    public void testUpdateFile(){
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
        dataHelper.updateAllFiles(files);

        dataHelper.updateFile(file2);

        files = dataHelper.getAllFiles();

        assertEquals(files.get(0).name,"File 1");
        assertEquals(files.get(0).owner,"Owner 2");
        assertEquals(files.get(0).status,"Status 2");
        assertEquals(files.get(0).path,"Path 2");
    }


}
