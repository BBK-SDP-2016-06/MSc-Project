package io;

import structure.TimeSeries;
import java.io.*;
import java.io.FileReader;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;


/**
 * Reads a test file that is selected by the user and populates a list of
 * TimeSeries objects according to what is read from the file.
 * Created by George Shiangoli on 20/07/2016.
 */
public class TestFileReaderImpl implements TestFileReader {

    private List<TimeSeries> testData;

    public TestFileReaderImpl(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            testData = reader.lines().filter(s -> !s.isEmpty())
                                     .map(InputUtils::toTimeSeries)
                                     .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getTimeSeriesCount() {
        return testData.size();
    }

    @Override
    public List<TimeSeries> getDataSet() {
        return testData;
    }

    @Override
    public TimeSeries getTimeSeries(int index) {
        return testData.get(index);
    }

    @Override
    public List<TimeSeries> getTimeSeriesOfClass(Integer... classTypes) {
        Set<Integer> classSet = Stream.of(classTypes).collect(toSet());
        return testData.parallelStream().filter(ts -> classSet.contains(ts.getClassType()))
                                        .collect(toList());
    }
}
