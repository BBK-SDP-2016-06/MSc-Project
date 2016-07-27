package exception;
import data.TimeSeries;

import java.util.List;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.toList;

/**
 * Decouples exception handling and throwing of exceptions from main application.
 * Created by George Shiangoli on 25/07/2016.
 */
public class ExceptionHandler {

    private static void validateTimeSeries(List<TimeSeries> input) {
        if(input.isEmpty()) {
            throw new FileFormatException("Empty file");
        }
        List<Integer> errorLines = getDataErrorIndices(input);
        if(!errorLines.isEmpty()) {
            throw new TimeSeriesFormatException("Number format error on lines: "
                    + errorLines.toString().substring(1, errorLines.toString().length() - 1));
        }
        List<Integer> missingDataLines = getMissingDataIndices(input);
        if(!missingDataLines.isEmpty()) {
            throw new TimeSeriesFormatException("Missing time series on lines: "
                    + missingDataLines.toString().substring(1, missingDataLines.toString().length() - 1));
        }
    }

    public static void validateTestTimeSeries(List<TimeSeries> input) {
        validateTimeSeries(input);
    }

    public static void validateTrainingTimeSeries(List<TimeSeries> input) {
        validateTimeSeries(input);
        List<Integer> missingClassLines = getClassErrorIndices(input);
        if(!missingClassLines.isEmpty()) {
            throw new ClassTypeException("Unlabelled time series on lines: "
                    + missingClassLines.toString().substring(1, missingClassLines.toString().length() - 1));
        }
    }

    public static List<Integer> getDataErrorIndices(List<TimeSeries> input) {
        return IntStream.range(0, input.size())
                .filter(i -> input.get(i) == null)
                .boxed()
                .collect(toList());
    }

    public static List<Integer> getMissingDataIndices(List<TimeSeries> input) {
        return IntStream.range(0, input.size())
                .filter(i -> input.get(i).getDataSize() == 0)
                .boxed()
                .collect(toList());
    }

    public static List<Integer> getClassErrorIndices(List<TimeSeries> input) {
        return IntStream.range(0, input.size())
                .filter(i -> input.get(i).getClassType() == -1)
                .boxed()
                .collect(toList());
    }

    public static void validatePAAFrames(long frames) {
        if (frames <= 0) {
            throw new IllegalArgumentException("Frame count cannot be 0 or negative.");
        }
    }

    public static void assessDataSize(List<Double> data, long frames) {
        if (data == null) {
            throw new IllegalArgumentException("Data list cannot be null.");
        }
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Data list cannot be empty");
        }
        if (frames > data.size()) {
            throw new IllegalArgumentException("Frame count (" + frames + ") " +
                    "cannot be greater than size of data sample (" + data.size() + ").");
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
}
