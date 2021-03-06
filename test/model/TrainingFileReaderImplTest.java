package model;

import classificationApp.model.exception.ClassTypeException;
import classificationApp.model.exception.FileFormatException;
import classificationApp.model.exception.TimeSeriesFormatException;
import classificationApp.model.io.TrainingFileReader;
import classificationApp.model.io.TrainingFileReaderImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;

/**
 * Tests for the TrainingFileReaderImpl class of the classificationApp.model.io package.
 * Created by George Shiangoli on 25/07/2016.
 */
public class TrainingFileReaderImplTest {

    private String path;
    private TrainingFileReader reader;

    @Before
    public void init() {
        path = new File("").getAbsolutePath() + File.separator
                + "test_resources" + File.separator
                + "TrainingReaderTest_Resources" + File.separator;
    }

    @Test (expected = FileFormatException.class)
    public void readingAnEmptyFileFails() {
        reader = new TrainingFileReaderImpl(path + "empty.txt");
    }

    @Test
    public void testGetClassCountOnValidSingleLineFile() {
        reader = new TrainingFileReaderImpl(path + "validSingleLine.txt");
        assertEquals(1, reader.getClassCount());
    }

    @Test
    public void testGetClassCountOnValidMultilineFile1() {
        reader = new TrainingFileReaderImpl(path + "validMultiLine1.txt");
        assertEquals(2, reader.getClassCount());
    }

    @Test
    public void testGetClassCountOnValidMultilineFile2() {
        reader = new TrainingFileReaderImpl(path + "validMultiLine2.txt");
        assertEquals(4, reader.getClassCount());
    }

    @Test
    public void testGetClassCountOnValidMultilineFile3() {
        reader = new TrainingFileReaderImpl(path + "validMultiLine3.txt");
        assertEquals(4, reader.getClassCount());
    }

    @Test
    public void testTimeSeriesLengthOnSingleLineFile() {
        reader = new TrainingFileReaderImpl(path + "validSingleLine.txt");
        assertEquals(6, reader.getTimeSeriesLength().getLowerBound());
        assertEquals(6, reader.getTimeSeriesLength().getUpperBound());
    }

    @Test
    public void testTimeSeriesLengthOnMultiLineFile1() {
        reader = new TrainingFileReaderImpl(path + "validMultiLine1.txt");
        assertEquals(286, reader.getTimeSeriesLength().getLowerBound());
        assertEquals(286, reader.getTimeSeriesLength().getUpperBound());
    }


    @Test
    public void testTimeSeriesLengthOnMultiLineFile2() {
        reader = new TrainingFileReaderImpl(path + "validMultiLine2.txt");
        assertEquals(345, reader.getTimeSeriesLength().getLowerBound());
        assertEquals(345, reader.getTimeSeriesLength().getUpperBound());
    }


    @Test
    public void testTimeSeriesLengthOnMultiLineFile3() {
        reader = new TrainingFileReaderImpl(path + "validMultiLine3.txt");
        assertEquals(345, reader.getTimeSeriesLength().getLowerBound());
        assertEquals(345, reader.getTimeSeriesLength().getUpperBound());
    }


    @Test
    public void testTimeSeriesLengthOnVariableLengthLineFile1() {
        try {
            reader = new TrainingFileReaderImpl(path + "variableLength1.txt");
            fail("expected TimeSeriesFormatException for invalid input");
        } catch (TimeSeriesFormatException e) {
            assertEquals("Time series data too short on lines: 0, 2", e.getMessage());
        }
    }

    @Test
    public void testTimeSeriesLengthOnVariableLengthLineFile2() {
        reader = new TrainingFileReaderImpl(path + "variableLength2.txt");
        assertEquals(2, reader.getTimeSeriesLength().getLowerBound());
        assertEquals(12, reader.getTimeSeriesLength().getUpperBound());
    }

    @Test (expected = TimeSeriesFormatException.class)
    public void invalidTimeSeriesFormatFails1() {
        reader = new TrainingFileReaderImpl(path + "invalid1.txt");
    }

    @Test
    public void emptyTimeSeriesLineFails() {
        try {
            reader = new TrainingFileReaderImpl(path + "emptyTimeSeries.txt");
            fail("expected TimeSeriesFormatException for invalid input");
        } catch (TimeSeriesFormatException e) {
            assertEquals("Missing time series on lines: 0", e.getMessage());
        }
    }

    @Test
    public void multipleEmptyTimeSeriesLineFails() {
        try {
            reader = new TrainingFileReaderImpl(path + "multiEmptyTimeSeries.txt");
            fail("expected TimeSeriesFormatException for invalid input");
        } catch (TimeSeriesFormatException e) {
            assertEquals("Missing time series on lines: 0, 2", e.getMessage());
        }
    }

    @Test
    public void unlabelledTimeSeriesLineFails1() {
        try {
            reader = new TrainingFileReaderImpl(path + "invalid2.txt");
            fail("expected ClassTypeException for missing class label");
        } catch (ClassTypeException e) {
            assertEquals("Unlabelled time series on lines: 0", e.getMessage());
        }
    }

    @Test
    public void unlabelledTimeSeriesLineFails2() {
        try {
            reader = new TrainingFileReaderImpl(path + "invalid3.txt");
            fail("expected ClassTypeException for missing class label");
        } catch (ClassTypeException e) {
            assertEquals("Unlabelled time series on lines: 5, 11", e.getMessage());
        }
    }

    @Test
    public void testGetClassListOnValidMultiLine1() {
        reader = new TrainingFileReaderImpl(path + "validMultiline1.txt");
        assertEquals(Arrays.asList(0, 1), reader.getClassList());
    }

    @Test
    public void testGetClassListOnValidMultiLine2() {
        reader = new TrainingFileReaderImpl(path + "validMultiline2.txt");
        assertEquals(Arrays.asList(1, 2, 3, 4), reader.getClassList());
    }

    @Test
    public void testGetClassListOnValidMultiLine3() {
        reader = new TrainingFileReaderImpl(path + "validMultiline3.txt");
        assertEquals(Arrays.asList(1, 2, 3, 4), reader.getClassList());
    }
}
