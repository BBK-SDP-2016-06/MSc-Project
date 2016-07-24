import data.TimeSeriesImpl;
import io.InputUtils;
import org.junit.Test;
import static org.junit.Assert.*;
import data.TimeSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


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
        TimeSeries series = InputUtils.toTimeSeries("0.5,1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5");
        assert series != null;
        assertEquals(-1, series.getClassType());
        assertEquals(10, series.getDataSize());
        assertTrue(Arrays.asList(0.5, 1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5).equals(series.getData()));
    }

    @Test
    public void testEmptyTimeSeries() {
        TimeSeries series = InputUtils.toTimeSeries("1");
        assert series != null;
        assertEquals(1, series.getClassType());
        assertEquals(0, series.getDataSize());
    }

    @Test
    public void invalidTimeSeriesResultsInNullTimeSeriesObject1() {
        TimeSeries series = InputUtils.toTimeSeries("1, a");
        assertNull(series);
    }

    @Test
    public void invalidTimeSeriesResultsInNullTimeSeriesObject2() {
        TimeSeries series = InputUtils.toTimeSeries("1, ,");
        assertNull(series);
    }

    @Test
    public void invalidTimeSeriesResultsInNullTimeSeriesObject3() {
        TimeSeries series = InputUtils.toTimeSeries("1, 1a, 0.5, 0.2");
        assertNull(series);
    }

    @Test
    public void invalidClassTypeResultsInNullTimeSeriesObject() {
        TimeSeries series = InputUtils.toTimeSeries("a, 1.5");
        assertNull(series);
    }

    @Test
    public void missingCommaInStringResultsInNullTimeSeriesObject() {
        TimeSeries series = InputUtils.toTimeSeries("1 1.5");
        assertNull(series);
    }

    @Test
    public void testGetErrorIndicesOnEmptyDataList() {
        List<TimeSeries> list = new ArrayList<>();
        List<Integer> errorIndices = InputUtils.getErrorIndices(list);
        assertEquals(0, errorIndices.size());
    }

    @Test
    public void testGetErrorIndicesOnCorrectDataList() {
        List<TimeSeries> list = new ArrayList<>();
        list.add(new TimeSeriesImpl(1, Arrays.asList(0.2, 0.4, 0.6, 0.8)));
        list.add(new TimeSeriesImpl(2, Arrays.asList(0.1, 0.3, 0.5, 0.7)));
        List<Integer> errorIndices = InputUtils.getErrorIndices(list);
        assertEquals(0, errorIndices.size());
    }

    @Test
    public void testGetErrorIndicesOnSingleInvalidEntry() {
        List<TimeSeries> list = new ArrayList<>();
        list.add(null);
        List<Integer> errorIndices = InputUtils.getErrorIndices(list);
        assertEquals(Collections.singletonList(0), errorIndices);
    }

    @Test
    public void testGetErrorIndicesOnMultipleInvalidEntry() {
        List<TimeSeries> list = new ArrayList<>();
        list.add(new TimeSeriesImpl(1, Arrays.asList(0.2, 0.4, 0.6, 0.8)));
        list.add(new TimeSeriesImpl(2, Arrays.asList(0.1, 0.3, 0.5, 0.7)));
        list.add(null);
        list.add(new TimeSeriesImpl(1, Arrays.asList(0.2, 0.4, 0.6, 0.8)));
        list.add(null);
        list.add(null);
        list.add(new TimeSeriesImpl(2, Arrays.asList(0.1, 0.3, 0.5, 0.7)));
        List<Integer> errorIndices = InputUtils.getErrorIndices(list);
        assertEquals(Arrays.asList(2, 4, 5), errorIndices);
    }

}
