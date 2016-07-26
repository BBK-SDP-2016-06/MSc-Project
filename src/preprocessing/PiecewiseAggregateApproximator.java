package preprocessing;

import exception.ExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reduces a time series data sample down to an approximation by splitting
 * the series into a specified fixed number of frames. A mean value is then
 * calculated from the time series data that falls within each frame, and
 * these new values then make up the approximate data sample. The frame value
 * specified by the user of the application controls the length of the
 * Piecewise Aggregate Approximation and hence the length of the resulting word.
 * Created by George Shiangoli on 26/07/2016.
 */
public class PiecewiseAggregateApproximator implements DataApproximator {

    private long frames;

    public PiecewiseAggregateApproximator(long frames) {
        ExceptionHandler.validatePAAFrames(frames);
        setFrames(frames);
    }

    @Override
    public List<Double> reduce(List<Double> input) {
        ExceptionHandler.assessDataSize(input, getFrames());
        List<List<Double>> partitionedData = partitionData(input);
        return partitionedData.parallelStream().map(MathUtils::getMean).collect(Collectors.toList());
    }

    public long getFrames() {
        return this.frames;
    }

    public void setFrames(long frames) {
        this.frames = frames;
    }

    private List<List<Double>> partitionData(List<Double> input) {
        double partitionSize = input.size() / getFrames();
        List<List<Double>> output = new ArrayList<>();
        List<Double> partition = new ArrayList<>();
        double remainingPartition = partitionSize;

        for (Double d : input) {

            if (remainingPartition > 1) {
                partition.add(d);
                remainingPartition--;
            } else {
                partition.add(d * remainingPartition);
                output.add(partition);
                double leftOver = 1 - remainingPartition;
                partition = new ArrayList<>();

                if (leftOver > 0) {
                    partition.add(d * leftOver);
                    remainingPartition = partitionSize - leftOver;
                } else {
                    remainingPartition = partitionSize;
                }
            }
        }
        return output;
    }

}
