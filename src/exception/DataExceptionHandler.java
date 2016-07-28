package exception;

import java.util.List;

/**
 * Decouples exception handling and throwing of exceptions from data package.
 * Created by George Shiangoli on 28/07/2016.
 */
public class DataExceptionHandler {

    public static void validateTimeSeries(int classType, List<Double> data) {
        if (classType < -1) throw new IllegalArgumentException("Class type cannot be a negative integer.");
        if (data == null) throw new IllegalArgumentException("Data list cannot be null");
    }

    public static void validateDiscretizedData(int classType, String word) {
        if (classType < -1) throw new IllegalArgumentException("Class type cannot be less than -1");
        if (word == null) throw new IllegalArgumentException("Word cannot be null.");
        if (word.isEmpty()) throw new IllegalArgumentException("Word cannot be empty.");
    }
}
