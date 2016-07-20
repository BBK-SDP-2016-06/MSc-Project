import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import structure.TimeSeries;
import structure.TimeSeriesImpl;

import java.util.Arrays;
import java.util.List;

/**
 * Testing class to assert behaviour of TimeSeries data structure.
 * Created by George Shiangoli on 20/07/2016.
 */
public class TimeSeriesImplTest {

    private List<Double> sampleData;

    @Before
    public void init() {
        sampleData = Arrays.asList(1.5987, 2.9125, 3.0050, -1.2345, -5.1194);
    }

    @Test
    public void testGetClassType() {
        for (int i = 1; i <= 10; i++) {
            TimeSeries series = new TimeSeriesImpl(i, sampleData);
            assertEquals(i, series.getClassType());
        }
    }

    @Test
    public void testGetData() {
        TimeSeries series = new TimeSeriesImpl(1, sampleData);
        assertEquals(sampleData, series.getData());
    }

    @Test
    public void testGetDataSize() {
        TimeSeries series = new TimeSeriesImpl(1, sampleData);
        assertEquals(5, series.getDataSize());
    }
}
