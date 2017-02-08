package classificationApp.model.io;

import classificationApp.model.data.DataLengthRange;
import classificationApp.model.data.TimeSeries;
import java.io.File;
import java.util.List;

/**
 * Responsible for the opening of input data files, and parsing the data into a format that
 * can be read and analysed by the application. The types of files that are able to be read
 * and written from are text (*.txt) and comma separated value (*.csv) files. The format of
 * each data file should adhere to the following rules:
 *     - each time series data sample should be written on its own line
 *     - each value written on a line must be separated by a comma character ','
 *     - every value should be in decimal notation and use a decimal point to denote that it is a
 *       double e.g. 2 should be written as 2.0
 *     - the exception to the above rule is the first value on each line. This may take the form of
 *       an integer and if so represents the class label of the time series sample. If the first value
 *       of a line is a double, it is assumed that the time series sample is unclassified and so the
 *       class label is unknown.
 * Should any of the above rules not pass, an exception with the appropriate feedback will be thrown and
 * reading of the file will fail.
 * Created by George Shiangoli on 20/07/2016.
 */
public interface FileReader {

    /**
     * Returns the number of time series data samples in the file, which should correspond to the number of
     * non-empty individual lines in the file.
     * @return the number of time series data samples in the file.
     */
    int getTimeSeriesCount();

    /**
     * Converts each line of the input file into a TimeSeries data object and returns the collection of
     * objects as a list.
     * @return list of TimeSeries objects created from reading each line of the input file
     */
    List<TimeSeries> getDataSet();

    /**
     * Returns a TimeSeries object from the list data structure constructed from the input file.
     * @param index the location within the list of TimeSeries objects that
     *              the required time series data object should be extracted from. Indexing begins at the
     *              zero position and increments subsequently.
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
    List<TimeSeries> getTimeSeriesOfClass(Integer... classTypes);

    /**
     * Returns a list of integers that represent all class types within the
     * given file.
     * @return the class types of the data set.
     */
    List<Integer> getClassList();

    /**
     * Returns a DataLengthRange object representing the range of time series lengths
     * present in the training file read by this object. Each DataLengthRange object holds a minimum
     * and maximum length corresponding to the shortest and longest time series data sample in the
     * current file.
     * @return the range of lengths of time series data.
     */
    DataLengthRange getTimeSeriesLength();

    /**
     * Returns a reference to the file used to retrieve the time series data.
     * @return this file that holds the time series data.
     */
    File getFile();
}
