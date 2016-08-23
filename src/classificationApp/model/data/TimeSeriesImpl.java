package classificationApp.model.data;

import classificationApp.model.exception.DataExceptionHandler;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the TimeSeries interface. Provides a concrete class
 * to hold information about individual time series classificationApp.model.data samples.
 * A TimeSeries of class type -1 is regarded as being unlabelled.
 * Created by George on 20/07/2016.
 */
public class TimeSeriesImpl implements TimeSeries {

    private Optional<Integer> classType;
    private List<Double> timeSeriesData;

    public TimeSeriesImpl(Optional<Integer> classType, List<Double> data) {
        DataExceptionHandler.validateTimeSeries(data);
        setClassType(classType);
        setData(data);
    }

    @Override
    public Optional<Integer> getClassType() {
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

    private void setClassType(Optional<Integer> classType) {
        this.classType = classType;
    }

    private void setData(List<Double> timeSeriesData) {
        this.timeSeriesData = timeSeriesData;
    }

    @Override
    public double getMax() {
        return timeSeriesData.parallelStream()
                .mapToDouble(d -> d)
                .max()
                .orElse(0);
    }

    @Override
    public double getMin() {
        return timeSeriesData.parallelStream()
                .mapToDouble(d -> d)
                .min()
                .orElse(0);
    }

    @Override
    public String toString() {
        return "Class Type: " + (getClassType().isPresent() ? getClassType().get() : "Unlabelled") + "\nTime Series: " + getData();
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
                                  .sum() * classType.orElse(-1) ;
    }
}
