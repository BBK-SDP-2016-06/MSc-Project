package io;

import structure.DataLengthRange;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * Reads a training file that is selected by the user and populates a map
 * data structure. The key value of the map corresponds to the class and each
 * class is associated with a list of time series.
 * Created by George Shiangoli on 18/07/2016.
 */
public class TrainingFileReader implements FileReader {

    private Map<Integer, List<List<Double>>> trainingData;

    public TrainingFileReader(String filePath) {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {
            List<List<String>> stringData = reader.lines()
                                                    .filter(line -> !line.trim().isEmpty())
                                                    .map(line -> Stream.of(line.split(","))
                                                            .filter(s -> !s.trim().isEmpty())
                                                            .collect(toList()))
                                                    .collect(toList());
            InputUtils.validateInput(stringData);
            trainingData = stringData.parallelStream().map(line -> line.stream()
                                                            .map(Double::parseDouble)
                                                            .collect(toList()))
                                                    .collect(groupingBy(dataSample -> dataSample.remove(0).intValue()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLineCount() {
        int count = 0;
        for (Map.Entry<Integer, List<List<Double>>> entry : trainingData.entrySet()) {
            count += entry.getValue().size();
        }
        return count;
    }

    @Override
    public int getClassCount() {
        return trainingData.size();
    }

    @Override
    public DataLengthRange getTimeSeriesLength() {
        List<Long> dataLengths = trainingData.values().parallelStream()
                                                      .flatMap(Collection::stream)
                                                      .map(List::size)
                                                      .map(Integer::longValue)
                                                      .collect(Collectors.toList());
        long lowerBound = dataLengths.parallelStream().mapToLong(Long::longValue).min().orElse(0);
        long upperBound = dataLengths.parallelStream().mapToLong(Long::longValue).max().orElse(0);
        return new DataLengthRange(lowerBound, upperBound);
    }

}
