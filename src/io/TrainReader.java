package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

/**
 * Reads a training file that is selected by the user and populates a map
 * data structure. The key value of the map corresponds to the class and each
 * class is associated with a list of time series.
 * Created by George Shiangoli on 18/07/2016.
 */
public class TrainReader implements CSVFileReader {

    private Map<Integer, List<List<Double>>> trainingData;

    public TrainReader(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            trainingData = reader.lines()
                    .filter(line -> !line.trim().isEmpty())
                    .map(line -> Stream.of(line.split(","))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .map(Double::parseDouble)
                            .collect(Collectors.toList()))
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
    public long getTimeSeriesLength() {
        Optional<List<List<Double>>> dataLine = trainingData.values().stream().findFirst();
        return dataLine.isPresent() ? dataLine.get().get(0).size() : 0;
    }

}
