package io;

import data.DataLengthRange;
import data.TimeSeries;
import exception.ExceptionHandler;

/**
 * Reads a training file as specified by the application user, converting the data lines into
 * a list of TimeSeries objects.
 * Created by George Shiangoli on 20/07/2016.
 */
public class TrainingFileReaderImpl extends FileReaderImpl implements TrainingFileReader {

    public TrainingFileReaderImpl(String filePath) {
        super(filePath);
        ExceptionHandler.validateTrainingTimeSeries(timeSeriesData);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getClassCount() {
        return (int) timeSeriesData.parallelStream()
                .map(TimeSeries::getClassType)
                .distinct()
                .count();
    }

    /**
     * @inheritDoc
     */
    @Override
    public DataLengthRange getTimeSeriesLength() {
        long upperBound = timeSeriesData.parallelStream().mapToLong(TimeSeries::getDataSize).max().orElse(0);
        long lowerBound = timeSeriesData.parallelStream().mapToLong(TimeSeries::getDataSize).min().orElse(0);
        return new DataLengthRange(lowerBound, upperBound);
    }
}