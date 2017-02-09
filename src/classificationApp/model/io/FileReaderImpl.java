package classificationApp.model.io;

import classificationApp.model.data.DataLengthRange;
import classificationApp.model.data.TimeSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * An implementation of the FileReader interface. Uses a list data structure to store the TimeSeries objects created,
 * which gets populated via the constructor. Validation of the individual data samples (lines within the file) are
 * specific to whether the file being read is a training or testing file and so will be implemented in the
 * respective subclasses.
 * Created by George Shiangoli on 24/07/2016.
 */
public abstract class FileReaderImpl implements FileReader {

    protected List<TimeSeries> timeSeriesData; //TimeSeries objects extracted from file line by line
    protected File dataFile; //reference to the file being opened and read from to populate the timeSeriesData field

    /**
     * The only constructor of this class uses the given filepath to open a link to the desired text or csv file. The
     * timeSeriesData field is then initialised and populated with each non empty line subsequently converted into a
     * TimeSeries object which is added to the list.
     * @param filePath a string that describes the absolute file location to the data file to be opened.
     */
    public FileReaderImpl(String filePath) {
        //try catch with resources eradicates the need to explicitly open and close file
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {

            //populate timeSeriesData field
            timeSeriesData = reader.lines()
                    .filter(s -> !s.isEmpty())
                    .map(String::trim)
                    .map(InputUtils::toTimeSeries)
                    .collect(toList());

            //initialises dataFile field to current file being opened
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
        //set used to ensure removal of duplicate values and enables "contains" method to be used efficiently (set theory)
        Set<Integer> classSet = Stream.of(classTypes).collect(toSet());
        return timeSeriesData.parallelStream()
                .filter(ts -> classSet.contains(ts.getClassType().get()))
                .collect(toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Integer> getClassList() {
        return timeSeriesData.parallelStream()
                .map(TimeSeries::getClassType)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .distinct()
                .sorted()
                .collect(toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public DataLengthRange getTimeSeriesLength() {

        //Retrieves the length of the longest time series data sample
        long upperBound = timeSeriesData.parallelStream()
                .mapToLong(TimeSeries::getDataSize)
                .max()
                .orElse(0);

        //Retrieves the length of the shortest time series data sample
        long lowerBound = timeSeriesData.parallelStream()
                .mapToLong(TimeSeries::getDataSize)
                .min()
                .orElse(0);

        return new DataLengthRange(lowerBound, upperBound);
    }

    @Override
    public File getFile() {
        return this.dataFile;
    }
}
