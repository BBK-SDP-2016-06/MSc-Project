import classification.ClassificationResult;
import classification.Classifier;
import classification.KNNClassifier;
import classification.LCSMeasure;
import data.DiscretizedData;
import data.DiscretizedDataImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

/**
 * Tests for the KNNClassifier class of the classification package.
 * Created by George Shiangoli on 30/07/2016.
 */
public class KNNClassifierTest {

    private DiscretizedData test;

    @Before
    public void init() {
        test = new DiscretizedDataImpl(-1, "abdcabdddccba");
    }

    @Test
    public void testClassifyMethodWithValidInputs1() {
        List<DiscretizedData> train = Arrays.asList(
                new DiscretizedDataImpl(1, "abbdcabda"),
                new DiscretizedDataImpl(1, "abadcabdc"),
                new DiscretizedDataImpl(1, "aabdcacda"),
                new DiscretizedDataImpl(1, "accdcabdd"),
                new DiscretizedDataImpl(2, "bbbbdacca"),
                new DiscretizedDataImpl(2, "bbabdadcd"),
                new DiscretizedDataImpl(2, "bccbdacca"),
                new DiscretizedDataImpl(2, "bbabdaaaa"),
                new DiscretizedDataImpl(3, "bbbcccbbb"),
                new DiscretizedDataImpl(3, "cbabbdccc"),
                new DiscretizedDataImpl(3, "abaacccca"),
                new DiscretizedDataImpl(3, "adddcacca")
        );
        Classifier classifier = new KNNClassifier(5, new LCSMeasure());
        ClassificationResult result = classifier.classify(test, train);
        assertEquals(1, result.getClassType());
        assertEquals(0.8, result.getConfidence(), 0.0);
    }

    @Test
    public void testClassifyMethodWithValidInputs2() {
        List<DiscretizedData> train = Arrays.asList(
                new DiscretizedDataImpl(1, "abbdcabda"),
                new DiscretizedDataImpl(1, "abadcabdc"),
                new DiscretizedDataImpl(1, "aabdcacda"),
                new DiscretizedDataImpl(1, "accdcabdd"),
                new DiscretizedDataImpl(2, "bbbbdacca"),
                new DiscretizedDataImpl(2, "bbabdadcd"),
                new DiscretizedDataImpl(2, "bccbdacca"),
                new DiscretizedDataImpl(2, "bbabdaaaa"),
                new DiscretizedDataImpl(3, "bbbcccbbb"),
                new DiscretizedDataImpl(3, "cbabbdccc"),
                new DiscretizedDataImpl(3, "abaacccca"),
                new DiscretizedDataImpl(3, "adddcacca")
        );
        Classifier classifier = new KNNClassifier(3, new LCSMeasure());
        ClassificationResult result = classifier.classify(test, train);
        assertEquals(1, result.getClassType());
        assertEquals(1.0, result.getConfidence(), 0.0);
    }
}
