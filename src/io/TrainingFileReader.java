package io;

import data.DataLengthRange;

/**
 * A file reader used convert a training file into a list of TimeSeries objects
 * that can be analysed and understood by the application.
 * Created by George Shiangoli on 23/07/2016.
 */
public interface TrainingFileReader extends FileReader {

    /**
     * Returns the total number of class labels within the training file read by
     * this object.
     * @return the number of classes / categories the training data is split into.
     */
    int getClassCount();

    /**
     * Returns a DataLengthRange object representing the range of time series lengths
     * present in the training file read by this object.
     * @return the range of lengths of time series data.
     */
    DataLengthRange getTimeSeriesLength();
}
