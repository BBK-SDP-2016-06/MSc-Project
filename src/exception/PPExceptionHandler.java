package exception;

import data.TimeSeries;

import java.util.List;

/**
 * Decouples exception handling and throwing of exceptions from preprocessing package.
 * Created by George Shiangoli on 28/07/2016.
 */
public class PPExceptionHandler {

    public static void validatePAAFrames(long frames) {
        if (frames <= 0) {
            throw new IllegalArgumentException("Frame count cannot be 0 or negative.");
        }
    }

    public static void assessData(TimeSeries data) {
        if (data == null) {
            throw new IllegalArgumentException("Data list cannot be null");
        }
        if (data.getData().isEmpty()) {
            throw new IllegalArgumentException("Data list cannot be empty");
        }
    }

    public static void validateAlphabetSize(int alphabetSize) {
        if (alphabetSize < 2) {
            throw new IllegalArgumentException("Alphabet size of " + alphabetSize + " is too small.");
        }
        if (alphabetSize > 26) {
            throw new IllegalArgumentException("Alphabet size of " + alphabetSize + " is too large.");
        }
    }

    public static void compareFrameAndDataSize(List<Double> data, long frames) {
        if (frames > data.size()) {
            throw new IllegalArgumentException("Frame count (" + frames + ") " +
                    "cannot be greater than size of data sample (" + data.size() + ").");
        }
    }
}
