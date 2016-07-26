package preprocessing;

import java.util.List;

/**
 * A utility class to hold various mathematical functions to help with the
 * pre-processing steps of the application model.
 * Created by George Shiangoli on 25/07/2016.
 */
public class MathUtils {

    /**
     * Calculates the mean of the input data by summing the values and then
     * dividing the intermediary result by the number of values.
     * @param inputData the list of values
     * @return the mean of the list of values. Returns NaN if the inputData is empty.
     */
    public static double getMean(List<Double> inputData) {
        return (inputData.parallelStream().mapToDouble(d -> d).sum()) / inputData.size();
    }
}
