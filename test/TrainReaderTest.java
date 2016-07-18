import io.CSVFileReader;
import io.TrainReader;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;

/**
 * Tests for the TrainReader class of the io package.
 * Created by George Shiangoli on 18/07/2016.
 */
public class TrainReaderTest {

    private static final String PATH = "..test_resources" + File.separator;

    private CSVFileReader reader;

    @Test
    public void testsGetLineCountOnEmptyFile() {
        reader = new TrainReader(PATH + "empty.txt");
        assertEquals(0, reader.getLineCount());
    }

    @Test
    public void testsGetLineCountOnSingleLineFile() {
        reader = new TrainReader(PATH + "single.txt");
        assertEquals(1, reader.getLineCount());
    }

}
