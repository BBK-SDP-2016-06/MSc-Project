package model;

import classificationApp.model.classification.ClassificationResult;
import classificationApp.model.classification.Classifier;
import classificationApp.model.classification.KNNClassifier;
import classificationApp.model.classification.LCSMeasure;
import classificationApp.model.data.DiscretizedData;
import classificationApp.model.data.DiscretizedDataImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

/**
 * Tests for the KNNClassifier class of the classificationApp.model.classification package.
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
        assertEquals(1, result.getPredClass());
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
        assertEquals(1, result.getPredClass());
    }
}
