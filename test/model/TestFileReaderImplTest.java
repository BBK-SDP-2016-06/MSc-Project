package model;

import classificationApp.model.exception.FileFormatException;
import classificationApp.model.exception.TimeSeriesFormatException;
import classificationApp.model.io.TestFileReader;
import classificationApp.model.io.TestFileReaderImpl;
import org.junit.Before;
import org.junit.Test;
import classificationApp.model.data.TimeSeries;
import classificationApp.model.data.TimeSeriesImpl;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for the TestFileReaderImpl class of the classificationApp.model.io package.
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

    @Test (expected = FileFormatException.class)
    public void readingAnEmptyFileFails() {
        reader = new TestFileReaderImpl(path + "empty.txt");
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

    @Test
    public void testGetTimeSeriesOnExample1() {
        reader = new TestFileReaderImpl(path + "exampleTest1.txt");
        assertEquals(new TimeSeriesImpl(1, Arrays.asList(0.01, 0.02, 0.03, 0.04, 0.05, 0.06, 0.07, 0.08, 0.09)), reader.getTimeSeries(0));
        assertEquals(new TimeSeriesImpl(2, Arrays.asList(-0.01, -0.02, -0.03, -0.04, -0.05, -0.06, -0.07, -0.08, -0.09)), reader.getTimeSeries(1));
        assertEquals(new TimeSeriesImpl(3, Arrays.asList(0.2, 0.4, 0.6, 0.8, 1.0, 1.2, 1.4, 1.6, 1.8, 2.0)), reader.getTimeSeries(2));
        assertEquals(new TimeSeriesImpl(2, Arrays.asList(-0.02, -0.04, -0.06, -0.08, -0.1, -0.12, -0.14, -0.16, -0.18)), reader.getTimeSeries(3));
        assertEquals(new TimeSeriesImpl(5, Arrays.asList(3.01, 3.02, 3.03, 3.04, 3.05, 3.06, 3.07, 3.08, 3.09)), reader.getTimeSeries(4));
    }

    @Test
    public void testGetTimeSeriesOfClassOnExample1() {
        reader = new TestFileReaderImpl(path + "exampleTest1.txt");
        List<TimeSeries> dataList = reader.getTimeSeriesOfClass(1);
        assertEquals(1, dataList.size());
        assertEquals(new TimeSeriesImpl(1, Arrays.asList(0.01, 0.02, 0.03, 0.04, 0.05, 0.06, 0.07, 0.08, 0.09)), dataList.get(0));
        for (TimeSeries ts : dataList) {
            assertEquals(1, ts.getClassType());
        }

        List<TimeSeries> dataList2 = reader.getTimeSeriesOfClass(2);
        assertEquals(2, dataList2.size());
        assertEquals(new TimeSeriesImpl(2, Arrays.asList(-0.01, -0.02, -0.03, -0.04, -0.05, -0.06, -0.07, -0.08, -0.09)), dataList2.get(0));
        assertEquals(new TimeSeriesImpl(2, Arrays.asList(-0.02, -0.04, -0.06, -0.08, -0.1, -0.12, -0.14, -0.16, -0.18)), dataList2.get(1));
        for (TimeSeries ts : dataList2) {
            assertEquals(2, ts.getClassType());
        }

        List<TimeSeries> dataList3 = reader.getTimeSeriesOfClass(2, 3, 5);
        assertEquals(4, dataList3.size());
    }

    @Test
    public void testFileReaderMethodsOnUnlabelledData() {
        reader = new TestFileReaderImpl(path + "unlabelledData.txt");
        assertEquals(2, reader.getTimeSeriesCount());
        assertEquals(-1, reader.getTimeSeries(0).getClassType());
        assertEquals(-1, reader.getTimeSeries(1).getClassType());

        assertEquals(8, reader.getTimeSeries(0).getDataSize());
        assertEquals(8, reader.getTimeSeries(1).getDataSize());

        assertEquals(new TimeSeriesImpl(-1, Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)), reader.getTimeSeries(0));
        assertEquals(new TimeSeriesImpl(-1, Arrays.asList(1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5)), reader.getTimeSeries(1));

    }

    @Test
    public void testFileReaderMethodsOnMixtureOfLabelledAndUnlabelledData() {
        reader = new TestFileReaderImpl(path + "labelledAndUnlabelledData.txt");
        assertEquals(4, reader.getTimeSeriesCount());
        assertEquals(-1, reader.getTimeSeries(0).getClassType());
        assertEquals(-1, reader.getTimeSeries(1).getClassType());
        assertEquals(1, reader.getTimeSeries(2).getClassType());
        assertEquals(2, reader.getTimeSeries(3).getClassType());

        assertEquals(8, reader.getTimeSeries(0).getDataSize());
        assertEquals(8, reader.getTimeSeries(1).getDataSize());
        assertEquals(8, reader.getTimeSeries(2).getDataSize());
        assertEquals(8, reader.getTimeSeries(3).getDataSize());

        assertEquals(new TimeSeriesImpl(-1, Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)), reader.getTimeSeries(0));
        assertEquals(new TimeSeriesImpl(-1, Arrays.asList(1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5)), reader.getTimeSeries(1));
        assertEquals(new TimeSeriesImpl(1, Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)), reader.getTimeSeries(2));
        assertEquals(new TimeSeriesImpl(2, Arrays.asList(1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5)), reader.getTimeSeries(3));
    }

    @Test
    public void testExceptionMessageOnInvalidInput1() {
        try {
            reader = new TestFileReaderImpl(path + "singleInvalidDataLine.txt");
            fail("expected TimeSeriesFormatException for invalid input");
        } catch (TimeSeriesFormatException e) {
            assertEquals("Number format error on lines: 0", e.getMessage());
        }
    }

    @Test
    public void testExceptionMessageOnInvalidInput2() {
        try {
            reader = new TestFileReaderImpl(path + "multipleInvalidDataLine.txt");
            fail("expected TimeSeriesFormatException for invalid input");
        } catch (TimeSeriesFormatException e) {
            assertEquals("Number format error on lines: 0, 2", e.getMessage());
        }
    }

    @Test
    public void testGetClassListOnUnlabelledData() {
        reader = new TestFileReaderImpl(path + "unlabelledData.txt");
        assertEquals(new ArrayList<>(), reader.getClassList());
    }

    @Test
    public void testGetClassOnLabelledAndUnlabelledData() {
        reader = new TestFileReaderImpl(path + "labelledAndUnlabelledData.txt");
        assertEquals(Arrays.asList(1, 2), reader.getClassList());
    }

    @Test
    public void testGetClassOnExampleTest() {
        reader = new TestFileReaderImpl(path + "exampleTest1.txt");
        assertEquals(Arrays.asList(1, 2, 3, 5), reader.getClassList());
    }

}
