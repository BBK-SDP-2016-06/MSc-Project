package classification;

import data.DiscretizedData;

import java.util.List;

/**
 * Represents a classification algorithm that uses a DistanceMeasure
 * upon a pre-processed set of training data and test sample, calculating
 * a likely class label for the test data, as well as a confidence
 * measure.
 * Created by George Shiangoli on 22/07/2016.
 */
public interface Classifier {

    /**
     * Performs the classification process according to a distance measure and
     * algorithm pre defined by the user. This method produces a predicted class
     * type for the test sample as well as a confidence measure supporting the
     * result.
     * @param test the sample of data, already discretized to be classified.
     * @param train the training set of data that the test sample will be compared
     *              to.
     * @return a ClassificationResult object produced as a result of the
     * classification process.
     */
    ClassificationResult classify(DiscretizedData test, List<DiscretizedData> train);
}
