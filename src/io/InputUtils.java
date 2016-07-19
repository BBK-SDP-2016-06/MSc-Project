package io;

import exception.*;

import java.util.List;

/**
 * Utility class used to maintain useful methods regarding IO
 * operations within the application.
 * Created by George on 19/07/2016.
 */
public class InputUtils {

    public static void validateInput(List<List<String>> input) {
        for(int i = 0; i < input.size(); i++) {
            try {
                validateLine(input.get(i));
            } catch (TrainingInputException e) {
                e.setLineNumber(i + 1);
                throw e;
            }
        }
    }

    public static void validateLine(List<String> line) {
        validateDataClass(line.get(0));
        validateTimeSeries(line);
    }

    private static void validateDataClass(String input) {
        try {
            Double d = Double.parseDouble(input);
            if(d != d.intValue()) {
                throw new NoDataClassException();
            }
        } catch (NumberFormatException e) {
            throw new InvalidDataClassException();
        }
    }

    private static void validateTimeSeries(List<String> input) {
        if(input.size() == 1) {
            throw new MissingTimeSeriesException();
        }
        for(int i = 1; i < input.size(); i++) {
            try {
                Double.parseDouble(input.get(i));
            } catch (NumberFormatException e) {
                throw new InvalidTimeSeriesException();
            }
        }
    }
}
