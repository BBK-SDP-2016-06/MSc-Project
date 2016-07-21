package structure;

import java.util.List;
import java.util.stream.DoubleStream;
import static java.util.stream.DoubleStream.*;

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

    @Override
    public String toString() {
        return "Class Type: " + getClassType() + "\nTime Series: " + getData();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof TimeSeries)) return false;
        TimeSeriesImpl that = (TimeSeriesImpl)other;
        return this.classType == that.classType && this.timeSeriesData.equals(that.timeSeriesData);
    }

    @Override
    public int hashCode() {
        return (int)timeSeriesData.parallelStream()
                                  .mapToDouble(Double::doubleValue)
                                  .sum() * classType ;
    }
}
