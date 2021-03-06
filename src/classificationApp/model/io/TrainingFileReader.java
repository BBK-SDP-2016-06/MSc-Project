package classificationApp.model.io;

/**
 * A file reader used convert a training file into a list of TimeSeries objects
 * that can be analysed and understood by the application.
 * Created by George Shiangoli on 23/07/2016.
 */
public interface TrainingFileReader extends FileReader {

    /**
     * Returns the total number of class labels within the training file read by
     * this object.
     * @return the number of classes / categories the training classificationApp.model.data is split into.
     */
    int getClassCount();
}
