package model.exception;
import model.data.TimeSeries;

import java.util.List;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.toList;

/**
 * Decouples model.exception handling and throwing of exceptions from model.io package.
 * Created by George Shiangoli on 25/07/2016.
 */
public class IOExceptionHandler {

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
}
