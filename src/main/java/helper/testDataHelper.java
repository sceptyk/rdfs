package helper;


import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashMap;

public class testDataHelper extends TestCase {

    @Test
    public void testGetSettings(){

        DataHelper data = new DataHelper();

        HashMap<String, String> setting1 = new HashMap<>();
        setting1.put("Key1","Value1");
        setting1.put("Key2","Value2");

        assertEquals(data.getSettings().containsKey("Key1"),true);
        assertEquals(data.getSettings().containsKey("Key2"),true);
        assertEquals(data.getSettings().get("Key1"),"Value1");


    }


}
