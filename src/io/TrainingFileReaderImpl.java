package io;

/**
 * Reads a training file as specified by the application user, converting the data lines into
 * a list of TimeSeries objects.
 * Created by George Shiangoli on 20/07/2016.
 */
public class TrainingFileReaderImpl extends FileReaderImpl implements TrainingFileReader {

    public TrainingFileReaderImpl(String filePath) {
        super(filePath);
    }
}