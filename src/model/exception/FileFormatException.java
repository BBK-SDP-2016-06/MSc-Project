package model.exception;

/**
 * Thrown when an empty training or test file is used, or if it is
 * not of the required format.
 * Created by George on 25/07/2016.
 */
public class FileFormatException extends RuntimeException {

    public FileFormatException(String errorMessage) {
        super(errorMessage);
    }
}
