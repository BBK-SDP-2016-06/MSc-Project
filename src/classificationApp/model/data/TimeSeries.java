package classificationApp.model.data;
import java.util.List;

/**
 * A class used to represent a single sample of time series classificationApp.model.data
 * within the application. This structure is comprised of an integer
 * field to represent the class of classificationApp.model.data, as well as a list of double
 * values that represents the actual time series.
 * Created by George Shiangoli on 20/07/2016.
 */
public interface TimeSeries {

    /**
     * Obtains the class that this TimeSeries is labelled with.
     * @return the integer value representing the class type of this
     * TimeSeries.
     */
    int getClassType();

    /**
     * Retrieves the list of classificationApp.model.data values of this TimeSeries.
     * @return the list of values of this TimeSeries.
     */
    List<Double> getData();

    /**
     * Calculates the number of classificationApp.model.data entries relating to this TimeSeries.
     * @return the size of the list used to store this TimeSeries classificationApp.model.data
     * values.
     */
    long getDataSize();

    /**
     * Returns the maximum value of the time series data.
     * @return the maximum value of this time series data.
     */
    double getMax();

    /**
     * Returns the minimum value of the time series data.
     * @return the minimum value of this time series data.
     */
    double getMin();
}
