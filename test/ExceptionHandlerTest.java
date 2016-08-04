import classificationApp.model.data.TimeSeries;
import classificationApp.model.data.TimeSeriesImpl;
import classificationApp.model.exception.IOExceptionHandler;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the IOExceptionHandler class of the classificationApp.model.exception package.
 * Created by George Shiangoli on 25/07/2016.
 */
public class ExceptionHandlerTest {

    @Test
    public void testGetErrorIndicesOnEmptyDataList() {
        List<TimeSeries> list = new ArrayList<>();
        List<Integer> errorIndices = IOExceptionHandler.getDataErrorIndices(list);
        assertEquals(0, errorIndices.size());
    }

    @Test
    public void testGetErrorIndicesOnCorrectDataList() {
        List<TimeSeries> list = new ArrayList<>();
        list.add(new TimeSeriesImpl(1, Arrays.asList(0.2, 0.4, 0.6, 0.8)));
        list.add(new TimeSeriesImpl(2, Arrays.asList(0.1, 0.3, 0.5, 0.7)));
        List<Integer> errorIndices = IOExceptionHandler.getDataErrorIndices(list);
        assertEquals(0, errorIndices.size());
    }

    @Test
    public void testGetErrorIndicesOnSingleInvalidEntry() {
        List<TimeSeries> list = new ArrayList<>();
        list.add(null);
        List<Integer> errorIndices = IOExceptionHandler.getDataErrorIndices(list);
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
        List<Integer> errorIndices = IOExceptionHandler.getDataErrorIndices(list);
        assertEquals(Arrays.asList(2, 4, 5), errorIndices);
    }
}
