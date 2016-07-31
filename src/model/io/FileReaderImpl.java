package model.io;

import model.data.TimeSeries;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * An implementation of the FileReader interface. Uses a list model.data structure
 * to store the TimeSeries objects created, which gets populated via the
 * constructor. Validation of the model.data sets are specific to whether the file
 * being read is a training or testing file and so will be implemented in the
 * respective subclasses.
 * Created by George Shiangoli on 24/07/2016.
 */
public abstract class FileReaderImpl implements FileReader {

    protected List<TimeSeries> timeSeriesData;

    public FileReaderImpl(String filePath) {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {
            timeSeriesData = reader.lines().filter(s -> !s.isEmpty())
                    .map(String::trim)
                    .map(InputUtils::toTimeSeries)
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getTimeSeriesCount() {
        return timeSeriesData.size();
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<TimeSeries> getDataSet() {
        return timeSeriesData;
    }

    /**
     * @inheritDoc
     */
    @Override
    public TimeSeries getTimeSeries(int index) {
        return timeSeriesData.get(index);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<TimeSeries> getTimeSeriesOfClass(Integer... classTypes) {
        Set<Integer> classSet = Stream.of(classTypes).collect(toSet());
        return timeSeriesData.parallelStream().filter(ts -> classSet.contains(ts.getClassType()))
                .collect(toList());
    }
}
