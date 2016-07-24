package exception;

/**
 * Thrown when a class type is not labelled correctly. This would be a result of
 * a training file having an unlabelled time series or a testing file with a class
 * label that does not match any labels in the corresponding training file.
 * Created by George Shiangoli on 24/07/2016.
 */
public class ClassTypeException extends RuntimeException {

    public ClassTypeException(String errorMessage) {
        super(errorMessage);
    }
}
