import io.TestFileReader;
import io.TestFileReaderImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;

/**
 * Tests for the TestFileReaderImpl class of the io package.
 * Created by George Shiangoli on 20/07/2016.
 */
public class TestFileReaderImplTest {

    private String path;
    private TestFileReader reader;

    @Before
    public void init() {
        path = new File("").getAbsolutePath() + File.separator
                + "test_resources" + File.separator
                + "TestReaderTest_Resources" + File.separator;
    }

    @Test
    public void testGetTimeSeriesCountOnEmptyFile() {
        reader = new TestFileReaderImpl(path + "empty.txt");
        assertEquals(0, reader.getTimeSeriesCount());
    }

    @Test
    public void testGetTimeSeriesCountOnNonEmptyFile() {
        reader = new TestFileReaderImpl(path + "nonEmpty.txt");
        assertEquals(2, reader.getTimeSeriesCount());
    }

}
