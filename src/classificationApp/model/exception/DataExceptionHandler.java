package classificationApp.model.exception;

import java.util.List;
import java.util.Optional;

/**
 * Decouples classificationApp.model.exception handling and throwing of exceptions from classificationApp.model.data package.
 * Created by George Shiangoli on 28/07/2016.
 */
public class DataExceptionHandler {

    public static void validateTimeSeries(List<Double> data) {
        if (data == null) throw new IllegalArgumentException("Data list cannot be null");
    }

    public static void validateDiscretizedData(String word) {
        if (word == null) throw new IllegalArgumentException("Word cannot be null.");
        if (word.isEmpty()) throw new IllegalArgumentException("Word cannot be empty.");
    }
}
