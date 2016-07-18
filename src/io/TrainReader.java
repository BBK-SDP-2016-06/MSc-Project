package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Reads a training file that is selected by the user.
 * Created by George Shiangoli on 18/07/2016.
 */
public class TrainReader implements CSVFileReader {

    private List<List<Double>> trainingData;

    public TrainReader(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            trainingData = reader.lines()
                    .filter(line -> !line.trim().isEmpty())
                    .map(line -> Stream.of(line.split(","))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .map(Double::parseDouble)
                            .collect(Collectors.toList()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLineCount() {
        return trainingData.size();
    }

}
