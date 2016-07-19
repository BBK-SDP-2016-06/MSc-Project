import exception.InvalidDataClassException;
import exception.InvalidTimeSeriesException;
import exception.MissingTimeSeriesException;
import exception.NoDataClassException;
import io.CSVFileReader;
import io.TrainReader;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;

/**
 * Tests for the TrainReader class of the io package.
 * Created by George Shiangoli on 18/07/2016.
 */
public class TrainReaderTest {

    private String path;
    private CSVFileReader reader;

    @Before
    public void init() {
        path = new File("").getAbsolutePath() + File.separator
                + "test_resources" + File.separator
        + "TrainReaderTest_Resources" + File.separator;
    }

    @Test
    public void testsGetLineCountOnEmptyFile() {
        reader = new TrainReader(path + "empty.txt");
        assertEquals(0, reader.getLineCount());
    }

    @Test
    public void testsGetLineCountOnSingleLineFile() {
        reader = new TrainReader(path + "single.txt");
        assertEquals(1, reader.getLineCount());
    }

    @Test
    public void testsGetLineCountOnMultiLineFile() {
        reader = new TrainReader(path + "lineCount.txt");
        assertEquals(12, reader.getLineCount());
    }

    @Test
    public void testConstructorOnEmptyStringAtEndOfLine() {
        reader = new TrainReader(path + "emptyEndOfLine.txt");
    }

    @Test
    public void testConstructorOnEmptyStringInMiddleOfLine() {
        reader = new TrainReader(path + "emptyMiddleOfLine.txt");
    }

    @Test
    public void testConstructorOnEmptyLine() {
        reader = new TrainReader(path + "emptyLine.txt");
        assertEquals(12, reader.getLineCount());
    }

    @Test
    public void testClassCountOnEmptyFile() {
        reader = new TrainReader(path + "empty.txt");
        assertEquals(0, reader.getClassCount());
    }

    @Test
    public void testClassCountOnSingleLineOfData() {
        reader = new TrainReader(path + "single.txt");
        assertEquals(1, reader.getClassCount());
    }

    @Test
    public void testClassCountOnMultipleLinesOfData() {
        reader = new TrainReader(path + "classCount.txt");
        assertEquals(8, reader.getClassCount());
    }

    @Test
    public void testTimeSeriesLengthOnEmptyFile() {
        reader = new TrainReader(path + "empty.txt");
        assertEquals(0, reader.getTimeSeriesLength());
    }

    @Test
    public void testTimeSeriesLengthOnSingleFile() {
        reader = new TrainReader(path + "single.txt");
        assertEquals(6, reader.getTimeSeriesLength());
    }

    @Test
    public void testTimeSeriesLengthOnMultipleLinesOfData() {
        reader = new TrainReader(path + "lineCount.txt");
        assertEquals(5, reader.getTimeSeriesLength());
    }

    @Test (expected = NoDataClassException.class)
    public void missingClassFailsConstructor1() {
        reader = new TrainReader(path + "missingClass1.txt");
    }

    @Test (expected = NoDataClassException.class)
    public void missingClassFailsConstructor2() {
        reader = new TrainReader(path + "missingClass2.txt");
    }

    @Test (expected = NoDataClassException.class)
    public void missingClassFailsConstructor3() {
        reader = new TrainReader(path + "missingClass3.txt");
    }

    @Test (expected = InvalidDataClassException.class)
    public void invalidClassFailsConstructor1() {
        reader = new TrainReader(path + "invalidClass1.txt");
    }

    @Test (expected = InvalidDataClassException.class)
    public void invalidClassFailsConstructor2() {
        reader = new TrainReader(path + "invalidClass2.txt");
    }

    @Test (expected = InvalidDataClassException.class)
    public void invalidClassFailsConstructor3() {
        reader = new TrainReader(path + "invalidClass3.txt");
    }

    @Test (expected = MissingTimeSeriesException.class)
    public void MissingTimeSeriesFailsConstructor1() {
        reader = new TrainReader(path + "missingTimeSeries1.txt");
    }

    @Test (expected = MissingTimeSeriesException.class)
    public void MissingTimeSeriesFailsConstructor2() {
        reader = new TrainReader(path + "missingTimeSeries2.txt");
    }

    @Test (expected = MissingTimeSeriesException.class)
    public void MissingTimeSeriesFailsConstructor3() {
        reader = new TrainReader(path + "missingTimeSeries3.txt");
    }

    @Test (expected = InvalidTimeSeriesException.class)
    public void invalidTimeSeriesFailsConstructor1() {
        reader = new TrainReader(path + "invalidTimeSeries1.txt");
    }

    @Test (expected = InvalidTimeSeriesException.class)
    public void invalidTimeSeriesFailsConstructor2() {
        reader = new TrainReader(path + "invalidTimeSeries2.txt");
    }

    @Test (expected = InvalidTimeSeriesException.class)
    public void invalidTimeSeriesFailsConstructor3() {
        reader = new TrainReader(path + "invalidTimeSeries3.txt");
    }

    @Test
    public void missingDataClassDetectedAtCorrectLine() {
        try {
            reader = new TrainReader(path + "missingClassLine5.txt");
        } catch (NoDataClassException e) {
            assertEquals(5, e.getLineNumber());
        }
    }

    @Test
    public void invalidDataClassDetectedAtCorrectLine() {
        try {
            reader = new TrainReader(path + "invalidClassLine2.txt");
        } catch (InvalidDataClassException e) {
            assertEquals(2, e.getLineNumber());
        }
    }

    @Test
    public void missingTimeSeriesDetectedAtCorrectLine() {
        try {
            reader = new TrainReader(path + "missingTimeSeriesLine10.txt");
        } catch (MissingTimeSeriesException e) {
            assertEquals(10, e.getLineNumber());
        }
    }

    @Test
    public void invalidTimeSeriesDetectedAtCorrectLine() {
        try {
            reader = new TrainReader(path + "invalidTimeSeriesLine1.txt");
        } catch (InvalidTimeSeriesException e) {
            assertEquals(1, e.getLineNumber());
        }
    }

}
