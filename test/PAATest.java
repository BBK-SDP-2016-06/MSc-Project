import org.junit.Test;
import static org.junit.Assert.*;
import model.preprocessing.DataApproximator;
import model.preprocessing.PiecewiseAggregateApproximator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Tests for the PiecewiseAggregateApproximator class of the model.preprocessing package.
 * Created by George Shiangoli on 26/07/2016.
 */
public class PAATest {

    @Test (expected = IllegalArgumentException.class)
    public void constructorWithFrameCountZeroFails() {
        new PiecewiseAggregateApproximator(0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void constructorWithNegativeFrameCountFails() {
        new PiecewiseAggregateApproximator(-3);
    }

    @Test
    public void reduceMethodFailsIfFrameCountIsGreaterThanSizeOfTimeSeries() {
        DataApproximator approximator = new PiecewiseAggregateApproximator(5);
        try {
            approximator.reduce(Arrays.asList(0.5, 1.5, 2.5, 3.5));
            fail("expected IllegalArgumentException with invalid input");
        } catch (IllegalArgumentException e) {
            assertEquals("Frame count (5) cannot be greater than size of model.data sample (4).", e.getMessage());
        }
    }

    @Test (expected = NullPointerException.class)
    public void reduceMethodFailsIfListIsNull() {
        DataApproximator approximator = new PiecewiseAggregateApproximator(5);
        approximator.reduce(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void reduceMethodFailsIfListIsEmpty() {
        DataApproximator approximator = new PiecewiseAggregateApproximator(5);
        approximator.reduce(new ArrayList<>());
    }

    @Test
    public void partitionDataMethodOnSingleValueListYieldsSameResult() {
        DataApproximator approximator = new PiecewiseAggregateApproximator(1);
        List<Double> approx =  approximator.reduce(Collections.singletonList(1.5));
        assertEquals(Collections.singletonList(1.5), approx);
    }

    @Test
    public void partitionDataMethodWithEqualFrameAndDataSizeYieldsSameResult() {
        DataApproximator approximator = new PiecewiseAggregateApproximator(3);
        List<Double> approx =  approximator.reduce(Arrays.asList(1.5, 2.5, 3.5));
        assertEquals(Arrays.asList(1.5, 2.5, 3.5), approx);
    }

    @Test
    public void testPartitionDataMethodWithDataSizeDivisibleByFrame() {
        DataApproximator approximator = new PiecewiseAggregateApproximator(3);
        List<Double> approx =  approximator.reduce(Arrays.asList(1.5, 2.5, 3.5, 4.5, 5.5, 6.5));
        assertEquals(Arrays.asList(2.0, 4.0, 6.0), approx);
    }

    @Test
    public void testReduceMethodWithNonDivisibleFrameCount() {
        DataApproximator approximator = new PiecewiseAggregateApproximator(3);
        List<Double> approx = approximator.reduce(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0));
        assertEquals(Arrays.asList(1.7143, 4.0, 6.2857), approx);
    }

    @Test
    public void testReduceMethodOnSampleData1() {
        DataApproximator approximator = new PiecewiseAggregateApproximator(7);
        List<Double> approx = approximator.reduce(Arrays.asList(2.02, 2.33, 2.99, 6.85, 9.20, 8.80, 7.50, 6.00, 5.85, 3.85, 4.85, 3.85, 2.22, 1.45, 1.34));
        assertEquals(Arrays.asList(2.2293, 5.6193, 8.6733, 6.36, 4.5833, 3.3313, 1.45), approx);
    }

    @Test
    public void testReduceMethodOnSampleData2() {
        DataApproximator approximator = new PiecewiseAggregateApproximator(9);
        List<Double> approx = approximator.reduce(Arrays.asList(2.02, 2.33, 2.99, 6.85, 9.20, 8.80, 7.50, 6.00, 5.85, 3.85, 4.85, 3.85, 2.22, 1.45, 1.34));
        assertEquals(Arrays.asList(2.144, 3.63, 8.26, 8.28, 6.27, 4.65, 4.45, 2.392, 1.384), approx);
    }

    @Test
    public void testReduceMethodOnSampleData3() {
        DataApproximator approximator = new PiecewiseAggregateApproximator(15);
        List<Double> approx = approximator.reduce(Arrays.asList(2.02, 2.33, 2.99, 6.85, 9.20, 8.80, 7.50, 6.00, 5.85, 3.85, 4.85, 3.85, 2.22, 1.45, 1.34));
        assertEquals(Arrays.asList(2.02, 2.33, 2.99, 6.85, 9.20, 8.80, 7.50, 6.00, 5.85, 3.85, 4.85, 3.85, 2.22, 1.45, 1.34), approx);
    }

    @Test
    public void testReduceMethodOnSampleData4() {
        DataApproximator approximator = new PiecewiseAggregateApproximator(1);
        List<Double> approx = approximator.reduce(Arrays.asList(2.02, 2.33, 2.99, 6.85, 9.20, 8.80, 7.50, 6.00, 5.85, 3.85, 4.85, 3.85, 2.22, 1.45, 1.34));
        assertEquals(Collections.singletonList(4.6067), approx);
    }
}
