package classificationApp.model.preprocessing;

import java.util.List;

/**
 * Converts an approximation produced by a DataApproximator to a word (String)
 * representation to be used by the DistanceMeasure within the classifier.
 * Created by George Shiangoli on 25/07/2016.
 */
public interface DataConverter {

    /**
     * Converts a list of double values into a symbolic String representation, whose
     * characters are determined from a fixed size domain alphabet, as specified by the
     * application user.
     * @param input an approximation of the input classificationApp.model.data time series.
     * @return a word representation of the input classificationApp.model.data time series.
     */
    String toWord(List<Double> input);
}
