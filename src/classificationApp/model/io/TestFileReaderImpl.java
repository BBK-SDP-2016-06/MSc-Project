package classificationApp.model.io;

import classificationApp.model.exception.IOExceptionHandler;

/**
 * Reads a test file as specified by the application user, converting the classificationApp.model.data lines into
 * a list of TimeSeries objects.
 * Created by George Shiangoli on 20/07/2016.
 */
public class TestFileReaderImpl extends FileReaderImpl implements TestFileReader {

    public TestFileReaderImpl(String filePath) {
        super(filePath);
        IOExceptionHandler.validateTestTimeSeries(timeSeriesData);
    }
}
