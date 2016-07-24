package io;

import data.TimeSeries;
import data.TimeSeriesImpl;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class used to maintain useful methods regarding IO
 * operations within the application.
 * Created by George on 19/07/2016.
 */
public class InputUtils {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean isLabelled(List<String> dataElements) {
        if(dataElements.isEmpty()) {
            return false;
        } else {
            String firstElement = dataElements.get(0);
            try {
                Integer.parseInt(firstElement);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    public static TimeSeries toTimeSeries(String dataLine) {
        List<String> splitLine = Stream.of(dataLine.split(",")).collect(Collectors.toList());

        int classLabel = isLabelled(splitLine) ? Integer.parseInt(splitLine.remove(0)) : -1;
        List<Double> timeSeriesData = splitLine.parallelStream()
                                           .map(Double::parseDouble)
                                           .collect(Collectors.toList());
        return new TimeSeriesImpl(classLabel, timeSeriesData);
    }
}
