package exception;

/**
 * Abstract class representing all exceptions that can be thrown
 * when reading and validating the input training file. It provides
 * additional functionality to all such exceptions in that they can hold
 * information about the line number that the error occurred, and hence
 * provide better feedback to the user.
 * Created by George Shiangoli on 19/07/2016.
 */
public abstract class TrainingInputException extends RuntimeException {

    private int lineNumber;

    public int getLineNumber() {
        return this.lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
