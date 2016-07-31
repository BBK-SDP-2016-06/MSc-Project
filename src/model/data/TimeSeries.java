package model.data;
import java.util.List;

/**
 * A class used to represent a single sample of time series model.data
 * within the application. This structure is comprised of an integer
 * field to represent the class of model.data, as well as a list of double
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
     * Retrieves the list of model.data values of this TimeSeries.
     * @return the list of values of this TimeSeries.
     */
    List<Double> getData();

    /**
     * Calculates the number of model.data entries relating to this TimeSeries.
     * @return the size of the list used to store this TimeSeries model.data
     * values.
     */
    long getDataSize();
}
