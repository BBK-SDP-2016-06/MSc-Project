package model.preprocessing;

import model.exception.PPExceptionHandler;
import org.apache.commons.math3.special.Erf;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This algorithm assumes that a normalized time series exhibits a Gaussian
 * distribution. The area under this curve is split into equal segments in
 * accordance with the size of the alphabet domain as specified by the application
 * user, establishing a set of 'breakpoints'. From these, a model.data sample can be
 * converted to a word ready for model.classification.
 * Created by George Shiangoli on 27/07/2016.
 */
public class GaussianSplitConverter implements DataConverter {

    private int alphabetSize;
    private List<Double> breakPoints;
    private static final int ASCII_DEC_OFFSET = 97;

    public GaussianSplitConverter(int alphabetSize) {
        PPExceptionHandler.validateAlphabetSize(alphabetSize);
        setAlphabetSize(alphabetSize);
        breakPoints = IntStream.range(1, getAlphabetSize())
                .mapToDouble(i -> getZValue((double)i / (double) getAlphabetSize()))
                .boxed().sorted().collect(Collectors.toList());
    }

    @Override
    public String toWord(List<Double> input) {
        return input.parallelStream().map(this::toSymbol)
                .reduce("", (char1, char2) -> char1 + char2);
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

    private String toSymbol(double inputValue) {
        for (int i = 0; i < breakPoints.size(); i++) {
            if (inputValue < breakPoints.get(i)) {
                return String.valueOf((char)(i + ASCII_DEC_OFFSET));
            }
        }
        return String.valueOf((char)(breakPoints.size() + ASCII_DEC_OFFSET));
    }
}
