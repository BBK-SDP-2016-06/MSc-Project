import io.TestFileReader;
import io.TestFileReaderImpl;
import org.junit.Before;
import org.junit.Test;
import structure.TimeSeries;
import structure.TimeSeriesImpl;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for the TestFileReaderImpl class of the io package.
 * Created by George Shiangoli on 20/07/2016.
 */
public class TestFileReaderImplTest {

    private String path;
    private TestFileReader reader;
    private List<TimeSeries> example1;

    @Before
    public void init() {
        path = new File("").getAbsolutePath() + File.separator
                + "test_resources" + File.separator
                + "TestReaderTest_Resources" + File.separator;
        example1 = new ArrayList<>();
        example1.add(new TimeSeriesImpl(1, Arrays.asList(0.01, 0.02, 0.03, 0.04, 0.05, 0.06, 0.07, 0.08, 0.09)));
        example1.add(new TimeSeriesImpl(2, Arrays.asList(-0.01, -0.02, -0.03, -0.04, -0.05, -0.06, -0.07, -0.08, -0.09)));
        example1.add(new TimeSeriesImpl(3, Arrays.asList(0.2, 0.4, 0.6, 0.8, 1.0, 1.2, 1.4, 1.6, 1.8, 2.0)));
        example1.add(new TimeSeriesImpl(2, Arrays.asList(-0.02, -0.04, -0.06, -0.08, -0.1, -0.12, -0.14, -0.16, -0.18)));
        example1.add(new TimeSeriesImpl(5, Arrays.asList(3.01, 3.02, 3.03, 3.04, 3.05, 3.06, 3.07, 3.08, 3.09)));
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

    @Test
    public void testGetTimeSeriesCountOnEmptyLine() {
        reader = new TestFileReaderImpl(path + "emptyLines.txt");
        assertEquals(5, reader.getTimeSeriesCount());
    }

    @Test
    public void testGetTimeSeriesCountOnExample1() {
        reader = new TestFileReaderImpl(path + "exampleTest1.txt");
        assertEquals(5, reader.getTimeSeriesCount());
    }

    @Test
    public void testGetDataSetOnExample1() {
        reader = new TestFileReaderImpl(path + "exampleTest1.txt");
        assertEquals(example1, reader.getDataSet());
    }

}
