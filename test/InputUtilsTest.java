import io.InputUtils;
import org.junit.Test;
import static org.junit.Assert.*;
import data.TimeSeries;

import java.util.Arrays;


/**
 * Tests for the static methods of the InputUtils class of the io package.
 * Created by George Shiangoli on 19/07/2016.
 */
public class InputUtilsTest {

    //Converting a string into TimeSeries object

    @Test
    public void testConvertingCorrectStringToTimeSeriesObject() {
        TimeSeries series = InputUtils.toTimeSeries("1, 0.5, 1.5, 2.5, 3.5, 4.5");
        assert series != null;
        assertEquals(1, series.getClassType());
        assertEquals(5, series.getDataSize());
        assertTrue(Arrays.asList(0.5, 1.5, 2.5, 3.5, 4.5).equals(series.getData()));
    }

    @Test
    public void testConvertingStringWithoutClassLabelToTimeSeriesObject() {
        TimeSeries series = InputUtils.toTimeSeries("0.5, 1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5");
        assert series != null;
        assertEquals(-1, series.getClassType());
        assertEquals(10, series.getDataSize());
        assertTrue(Arrays.asList(0.5, 1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5).equals(series.getData()));
    }

}
