package preprocessing;

import java.util.List;

/**
 * A utility class to hold various mathematical functions to help with the
 * pre-processing steps of the application model.
 * Created by George Shiangoli on 25/07/2016.
 */
public class MathUtils {

    public static double getMean(List<Double> inputData) {
        return (inputData.parallelStream().mapToDouble(d -> d).sum()) / inputData.size();
    }
}
