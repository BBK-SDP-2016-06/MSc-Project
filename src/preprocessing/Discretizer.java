package preprocessing;

import data.TimeSeries;

import java.util.List;
import java.util.stream.Collectors;
import static preprocessing.MathUtils.*;

/**
 * Transforms a time series data representation into
 * a symbolic String format known as a Word.
 * Created by George Shiangoli on 22/07/2016.
 */
public abstract class Discretizer {

    protected DataApproximator approximator;
    protected DataConverter converter;
    private TimeSeries rawData;

    public Discretizer(TimeSeries rawData, DataApproximator approximator, DataConverter converter) {
        this.rawData = rawData;
        this.approximator = approximator;
        this.converter = converter;
    }

    /**
     * Executes the discretization process upon the TimeSeries data object used to construct
     * this object. This involves checking first whether the data is in a z-normalized form
     * and performing the normalization process if required. The normalized data is then
     * transformed into a reduced format before being converting into a String word ready
     * for the classification process.
     * @return a String representation of the time series data of this TimeSeries object.
     */
    public final String executeDiscretization() {
        List<Double> normalizedData = normalizeData(rawData.getData());
        List<Double> transformedData = approximator.reduce(normalizedData);
        return converter.toWord(transformedData);
    }

    /**
     * Z-Normalizes a list of double values so that their mean is 0 and standard deviation is 1.
     * This is to ensure that the effect of any offset and scaling amongst the data samples are
     * nullified.
     * @param input data obtained from this TimeSeries object.
     * @return the input data in z-normalized form.
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
