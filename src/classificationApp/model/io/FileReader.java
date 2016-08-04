package classificationApp.model.io;

import classificationApp.model.data.TimeSeries;
import java.util.List;

/**
 * Charged with the opening and reading of input classificationApp.model.data files that are to
 * be used in the classificationApp.model.classification process, converting the csv / txt
 * streams into a classificationApp.model.data structure recognised as by the application.
 * Created by George Shiangoli on 20/07/2016.
 */
public interface FileReader {

    /**
     * Returns the number of time series classificationApp.model.data samples in the file.
     * @return the number of time series classificationApp.model.data samples in the file.
     */
    int getTimeSeriesCount();

    /**
     * Returns the entire list of TimeSeries classificationApp.model.data objects obtained from
     * reading the input file.
     * @return list of TimeSeries objects representing all time series
     * classificationApp.model.data samples within the given file.
     */
    List<TimeSeries> getDataSet();

    /**
     * Returns a TimeSeries object from the list classificationApp.model.data structure constructed
     * from the input file.
     * @param index the location within the list of TimeSeries objects that
     *              the required time series classificationApp.model.data should be extracted from.
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
}
