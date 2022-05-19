package readIO;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

}
