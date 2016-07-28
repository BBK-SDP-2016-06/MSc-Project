package preprocessing;

import java.util.List;

/**
 * Transforms a time series data set into new approximation in order to
 * both reduce the dimensionality and complexity. This enables further
 * pre-processing and classification to be performed with greater efficiency
 * on the selected time series data.
 * Created by George Shiangoli on 25/07/2016.
 */
public interface DataApproximator {

    /**
     * Reduces an input time series data set in terms of dimensionality and
     * complexity, while still aiming to retain its structural characteristics.
     * @param input the input time series data sample.
     * @return a reduced approximation of the input data sample.
     */
    List<Double> reduce(List<Double> input);
}
