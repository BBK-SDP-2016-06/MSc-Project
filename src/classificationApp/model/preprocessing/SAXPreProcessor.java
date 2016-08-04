package classificationApp.model.preprocessing;

/**
 * Symbolic Aggregation Approximation method of discretization.
 * Uses a Piecewise Aggregate Approximation to convert a time series
 * into a reduced form. The SAXPreProcessor also assumes that the normalised
 * classificationApp.model.data exhibits a Gaussian distribution, which is used to convert the
 * reduced approximation to a String word ready for classificationApp.model.classification.
 * Created by George Shiangoli on 28/07/2016.
 */
public class SAXPreProcessor extends PreProcessor {

    /**
     * Constructs a SAXPreProcessor object using two parameters that can be
     * controlled by the application user.
     * @param frameCount the length of the word that will be output when calling
     *                   the discretize method.
     * @param alphabetSize the size of the domain of symbols that the time series
     *                     will be converted to.
     */
    public SAXPreProcessor(long frameCount, int alphabetSize) {
        super(new PiecewiseAggregateApproximator(frameCount),
                new GaussianSplitConverter(alphabetSize));
    }
}
