package readIO;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Auther:
 * @Date: 2022/5/17 16:13
 * @Description:
 */

public class ReaderTest {

    Reader reader;

    @Before
    public void setUp() {
        reader = new Reader("Resources/DXYArea.csv");
    }

    @Test
    public void testReader() {
        assertEquals("DXYArea.csv", reader.getFileName());
        assertEquals("Resources/", reader.getFilePath());
    }

    @Test
    public void testIsCsvFile() {
        assertTrue(reader.isCsvFile("DXYArea.csv"));
        assertFalse(reader.isCsvFile("DXYArea.txt"));
    }

}
