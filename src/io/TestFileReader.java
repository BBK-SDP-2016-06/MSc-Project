package io;

import structure.TimeSeries;

import java.util.List;

/**
 * Charged with the opening and reading of test files that are to
 * undergo the classification process, converting the csv / txt
 * streams into a data structure recognised by the application.
 * Created by George Shiangoli on 20/07/2016.
 */
public interface TestFileReader {

    /**
     * Returns the number of time series data samples in the file.
     * @return the number of time series data samples in the file.
     */
    int getTimeSeriesCount();

    /**
     * Returns the entire list of TimeSeries data objects obtained from
     * reading the Test input file.
     * @return list of TimeSeries objects representing all time series
     * data samples within the given file.
     */
    List<TimeSeries> getDataSet();

    /**
     * Returns a TimeSeries object from the list data structure constructed
     * from the Test input file.
     * @param index the location within the list of TimeSeries objects that
     *              the required time series data should be extracted from.
     * @throws IndexOutOfBoundsException if the specified index is greater
     * than or equal to the size of the list, or a negative number.
     * @return the TimeSeries object at the given location in the list.
     */
    TimeSeries getTimeSeries(int index);

    /**
     * Returns a list of TimeSeries objects that are labelled with the desired
     * class types given.
     * @param classTypes the list of class types that the resulting list of
     *                   TimeSeries objects should have.
     * @return a list of all TimeSeries objects generated from the Test input
     * file that have the required class types as labels.
     */
    List<TimeSeries> getTimeSeriesOfClass(int... classTypes);
}
