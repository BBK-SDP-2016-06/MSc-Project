package model.data;

import model.exception.DataExceptionHandler;

import java.util.List;

/**
 * Implementation of the TimeSeries interface. Provides a concrete class
 * to hold information about individual time series model.data samples.
 * A TimeSeries of class type -1 is regarded as being unlabelled.
 * Created by George on 20/07/2016.
 */
public class TimeSeriesImpl implements TimeSeries {

    private int classType;
    private List<Double> timeSeriesData;

    public TimeSeriesImpl(int classType, List<Double> data) {
        DataExceptionHandler.validateTimeSeries(classType, data);
        setClassType(classType);
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
