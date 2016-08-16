package classificationApp.model.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import static java.lang.Math.*;

/**
 * A utility class to hold various mathematical functions to help with the
 * pre-processing steps of the application classificationApp.model.
 * Created by George Shiangoli on 25/07/2016.
 */
public class MathUtils {

    private static final double ERROR_MARGIN = 0.001;

    /**
     * Calculates the mean of the input classificationApp.model.data by summing the values and then
     * dividing the intermediary result by the number of values.
     * @param inputData the list of double values
     * @return the mean of the list of values. Returns NaN if the inputData is empty.
     */
    public static double getMean(List<Double> inputData) {
        return (inputData.parallelStream().mapToDouble(d -> d).sum()) / inputData.size();
    }

    /**
     * Calculates the standard deviation of the input classificationApp.model.data. The result expresses
     * how much the values of the input classificationApp.model.data differ from the mean value of the classificationApp.model.data
     * as a whole.
     * @param inputData the list of double values
     * @return the standard deviation of the list of values.
     */
    public static double getStandardDeviation(List<Double> inputData) {
        if (inputData.isEmpty()) {
            return Double.NaN;
        } else if (inputData.size() == 1) {
            return 0;
        } else {
            double mean = getMean(inputData);
            return sqrt(inputData.parallelStream().mapToDouble(d -> pow((d - mean), 2)).sum() / (inputData.size() - 1));
        }
    }

    /**
     * Tests whether the input classificationApp.model.data has already been z-normalized. A classificationApp.model.data sample is said to
     * be z-normalised if its mean is 0 and standard deviation is 1.
     * @param inputData the list of double values to be tested.
     * @return true if the classificationApp.model.data is z-normalized, false otherwise.
     */
    public static boolean isZNormalized(List<Double> inputData) {
        return abs(getMean(inputData) - 0) < ERROR_MARGIN
                && abs(getStandardDeviation(inputData) - 1) < ERROR_MARGIN;
    }

    /**
     * Rounds a double value to 5 significant figures.
     * @param input double value.
     * @return the input double value rounded to 5 significant figures.
     */
    public static double to5SF(double input) {
        BigDecimal bd = new BigDecimal(input);
        return bd.round(new MathContext(5)).doubleValue();
    }
}
