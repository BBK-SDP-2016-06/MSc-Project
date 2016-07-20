package structure;

import java.util.List;

/**
 * Implementation of the TimeSeries interface. Provides a concrete class
 * to hold information about individual time series data samples.
 * Created by George on 20/07/2016.
 */
public class TimeSeriesImpl implements TimeSeries {

    private int classType;
    private List<Double> timeSeriesData;

    public TimeSeriesImpl(int classType, List<Double> data) {
        setClassType(classType);
        setData(data);
    }

    @Override
    public int getClassType() {
        return classType;
    }

    @Override
    public List<Double> getData() {
        return null;
    }

    @Override
    public long getDataSize() {
        return 0;
    }

    private void setClassType(int classType) {
        this.classType = classType;
    }

    private void setData(List<Double> timeSeriesData) {
        this.timeSeriesData = timeSeriesData;
    }
}
