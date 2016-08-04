package classificationApp.model.io;

/**
 * A file reader used convert a training file into a list of TimeSeries objects
 * that can be analysed and understood by the application.
 * Created by George Shiangoli on 23/07/2016.
 */
public interface TestFileReader extends FileReader {

    /**
     * Returns the maximum value within the time series.
     * @return max value of the time series.
     */
    double getMax(int index);

    /**
     * Returns the minimum value within the time series.
     * @return minimum value of the time series.
     */
    double getMin(int index);

}
