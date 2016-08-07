package classificationApp.model.io;

import classificationApp.model.data.TimeSeries;
import classificationApp.model.preprocessing.MathUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * An implementation of the FileReader interface. Uses a list classificationApp.model.data structure
 * to store the TimeSeries objects created, which gets populated via the
 * constructor. Validation of the classificationApp.model.data sets are specific to whether the file
 * being read is a training or testing file and so will be implemented in the
 * respective subclasses.
 * Created by George Shiangoli on 24/07/2016.
 */
public abstract class FileReaderImpl implements FileReader {

    protected List<TimeSeries> timeSeriesData;
    protected File dataFile;

    public FileReaderImpl(String filePath) {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {
            timeSeriesData = reader.lines().filter(s -> !s.isEmpty())
                    .map(String::trim)
                    .map(InputUtils::toTimeSeries)
                    .collect(toList());
            dataFile = new File(filePath);
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

    /**
     * @inheritDoc
     */
    @Override
    public List<Integer> getClassList() {
        return timeSeriesData.parallelStream().map(TimeSeries::getClassType)
                .filter(i -> i != -1).distinct().sorted().collect(toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isNormalized(int index) {
        return MathUtils.isZNormalized(getTimeSeries(index).getData());
    }

    @Override
    public File getFile() {
        return this.dataFile;
    }
}
