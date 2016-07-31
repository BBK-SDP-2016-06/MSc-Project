package model.exception;

/**
 * Thrown to indicate when one or more time series model.data samples read from an input
 * file is not formatted correctly.
 * Created by George Shiangoli on 24/07/2016.
 */
public class TimeSeriesFormatException extends RuntimeException {

    public TimeSeriesFormatException(String errorMessage) {
        super(errorMessage);
    }
}
