package classificationApp.model.preprocessing;

import classificationApp.model.data.TimeSeries;
import classificationApp.model.exception.PPExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;
import static classificationApp.model.math.MathUtils.*;

/**
 * Transforms a time series classificationApp.model.data representation into
 * a symbolic String format known as a Word.
 * Created by George Shiangoli on 22/07/2016.
 */
public abstract class PreProcessor {

    private DataApproximator approximator;
    private DataConverter converter;

    public PreProcessor(DataApproximator approximator, DataConverter converter) {
        this.approximator = approximator;
        this.converter = converter;
    }

    /**
     * Executes the discretization process upon a TimeSeries classificationApp.model.data object. This involves checking
     * first whether the classificationApp.model.data is in a z-normalized form and performing the normalization process if
     * required. The normalized classificationApp.model.data is then transformed into a reduced format before being converting
     * into a String word ready for the classificationApp.model.classification process.
     * @param rawData the TimeSeries object upon which the discretization process will take place.
     * @return a String representation of the time series classificationApp.model.data of this TimeSeries object.
     */
    public final String discretize(TimeSeries rawData) {
        PPExceptionHandler.assessData(rawData);
        List<Double> normalizedData = normalizeData(rawData.getData());
        List<Double> transformedData = approximator.reduce(normalizedData);
        return converter.toWord(transformedData);
    }

    /**
     * Z-Normalizes a list of double values so that their mean is 0 and standard deviation is 1.
     * This is to ensure that the effect of any offset and scaling amongst the classificationApp.model.data samples are
     * nullified.
     * @param input classificationApp.model.data obtained from this TimeSeries object.
     * @return the input classificationApp.model.data in z-normalized form.
     */
    private List<Double> normalizeData(List<Double> input) {
        if (isZNormalized(input)) {
            return input;
        } else {
            return input.parallelStream()
                    .map(d -> (d - getMean(input)) / getStandardDeviation(input))
                    .collect(Collectors.toList());
        }
    }
}
