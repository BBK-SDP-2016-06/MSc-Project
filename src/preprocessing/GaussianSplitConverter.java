package preprocessing;

import exception.ExceptionHandler;
import org.apache.commons.math3.special.Erf;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This algorithm assumes that a normalized time series exhibits a Gaussian
 * distribution. The area under this curve is split into equal segments in
 * accordance with the size of the alphabet domain as specified by the application
 * user, establishing a set of 'breakpoints'. From these, a data sample can be
 * converted to a word ready for classification.
 * Created by George Shiangoli on 27/07/2016.
 */
public class GaussianSplitConverter implements DataConverter {

    private int alphabetSize;
    private List<Double> breakPoints;

    public GaussianSplitConverter(int alphabetSize) {
        ExceptionHandler.validateAlphabetSize(alphabetSize);
        setAlphabetSize(alphabetSize);
        breakPoints = IntStream.range(1, getAlphabetSize())
                .mapToDouble(i -> getZValue((double)i / (double) getAlphabetSize()))
                .boxed().sorted().collect(Collectors.toList());
    }

    @Override
    public String toWord(List<Double> input) {
        return null;
    }

    private int getAlphabetSize() {
        return alphabetSize;
    }

    private void setAlphabetSize(int alphabetSize) {
        this.alphabetSize = alphabetSize;
    }

    private double getZValue(double probability) {
        double zValue = Math.sqrt(2) * Erf.erfcInv(2 * probability);
        return MathUtils.to5SF(zValue);
    }

    private char toSymbol(double inputValue) {
        return '?';
    }
}
