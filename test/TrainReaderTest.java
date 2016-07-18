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
        path = new File("").getAbsolutePath() + File.separator + "test_resources" + File.separator;
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

}
