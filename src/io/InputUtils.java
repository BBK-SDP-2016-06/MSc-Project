package io;

import exception.InvalidDataClassException;
import exception.InvalidTimeSeriesException;
import exception.NoDataClassException;
import java.util.List;

/**
 * Utility class used to maintain useful methods regarding IO
 * operations within the application.
 * Created by George on 19/07/2016.
 */
public class InputUtils {

    public static void validateInput(List<String> input) {
        validateDataClass(input.get(0));
        validateTimeSeries(input);
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
        for(int i = 1; i < input.size(); i++) {
            try {
                Double.parseDouble(input.get(i));
            } catch (NumberFormatException e) {
                throw new InvalidTimeSeriesException();
            }
        }
    }
}
