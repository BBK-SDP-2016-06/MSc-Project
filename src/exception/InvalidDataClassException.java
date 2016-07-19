package exception;

/**
 * This exception is thrown when the data class specified for
 * a particular time series is not in the required format. Class
 * names accepted by the application should be integers. A class
 * formatted as a double will be confused as a member of the time
 * series data itself and a class labelled as a non numeric value
 * wil throw this exception.
 * Created by George Shiangoli on 19/07/2016.
 */
public class InvalidDataClassException extends RuntimeException {}
