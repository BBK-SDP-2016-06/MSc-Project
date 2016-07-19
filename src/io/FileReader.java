package io;

import structure.DataLengthRange;

/**
 * Charged with the handling of reading in the TEST and TRAINING files
 * used for classification purposes. Valid files that can be read include
 * .csv and .txt files. An attempt to open and read from a file that isn't
 * of the aforementioned types result in an error.
 * Created by George Shiangoli on 18/07/2016.
 */
public interface FileReader {

    /**
     * Retrieves the number of lines within the file that is being read. This
     * should equate to the number of time series data records present in the file.
     * @return the number of lines in this file
     */
    int getLineCount();

    /**
     * Retrieves the number of different classes that are present in the current data set.
     * @return the number of classes in the file
     */
    int getClassCount();

    /**
     * Retrieves the length range of the individual time series samples within this file. i.e.
     * the number of values recorded for each time series sample.
     * @return the range of length of the time series in the file
     */
    DataLengthRange getTimeSeriesLength();
}
