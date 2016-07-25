package io;

import exception.ExceptionHandler;

/**
 * Reads a test file as specified by the application user, converting the data lines into
 * a list of TimeSeries objects.
 * Created by George Shiangoli on 20/07/2016.
 */
public class TestFileReaderImpl extends FileReaderImpl implements TestFileReader {

    public TestFileReaderImpl(String filePath) {
        super(filePath);
        ExceptionHandler.validateTestTimeSeries(timeSeriesData);
    }
}
