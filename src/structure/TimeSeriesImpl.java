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
        if (classType < 0) {
            throw new IllegalArgumentException("Class type cannot be a negative integer.");
        }
        setClassType(classType);
        if (data == null) {
            throw new IllegalArgumentException("Data list cannot be null");
        }
        setData(data);
    }

    @Override
    public int getClassType() {
        return classType;
    }

    @Override
    public List<Double> getData() {
        return timeSeriesData;
    }

    @Override
    public long getDataSize() {
        return timeSeriesData.size();
    }

    private void setClassType(int classType) {
        this.classType = classType;
    }

    private void setData(List<Double> timeSeriesData) {
        this.timeSeriesData = timeSeriesData;
    }
}
