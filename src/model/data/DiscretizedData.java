package model.data;


/**
 * A class used to represent a single sample of time series model.data
 * within the application that has undergone the discretization
 * process. This structure is comprised of an integer field to
 * represent the original class of model.data, as well as a string that
 * represents a symbolic representation of the actual time series.
 * Created by George Shiangoli on 28/07/2016.
 */
public interface DiscretizedData {

    /**
     * Obtains the class that the original time series was labelled with.
     * @return the integer value representing the class type of this
     * DiscretizedData.
     */
    int getClassType();

    /**
     * Retrieves the word representation of the original time series.
     * @return the word approximation.
     */
    String getWord();
}
