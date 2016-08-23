package classificationApp.model.io;
import classificationApp.model.exception.IOExceptionHandler;

/**
 * Reads a training file as specified by the application user, converting the classificationApp.model.data lines into
 * a list of TimeSeries objects.
 * Created by George Shiangoli on 20/07/2016.
 */
public class TrainingFileReaderImpl extends FileReaderImpl implements TrainingFileReader {

    public TrainingFileReaderImpl(String filePath) {
        super(filePath);
        IOExceptionHandler.validateTrainingTimeSeries(timeSeriesData);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getClassCount() {
        return (int) timeSeriesData.parallelStream()
                .filter(ts -> ts.getClassType().isPresent())
                .map(ts -> ts.getClassType().get())
                .distinct()
                .count();
    }
}