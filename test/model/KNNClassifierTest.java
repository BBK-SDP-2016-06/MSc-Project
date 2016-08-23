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
import java.util.Optional;

/**
 * Tests for the KNNClassifier class of the classificationApp.model.classification package.
 * Created by George Shiangoli on 30/07/2016.
 */
public class KNNClassifierTest {

    private DiscretizedData test;

    @Before
    public void init() {
        test = new DiscretizedDataImpl(Optional.empty(), "abdcabdddccba");
    }

    @Test
    public void testClassifyMethodWithValidInputs1() {
        List<DiscretizedData> train = Arrays.asList(
                new DiscretizedDataImpl(Optional.of(1), "abbdcabda"),
                new DiscretizedDataImpl(Optional.of(1), "abadcabdc"),
                new DiscretizedDataImpl(Optional.of(1), "aabdcacda"),
                new DiscretizedDataImpl(Optional.of(1), "accdcabdd"),
                new DiscretizedDataImpl(Optional.of(2), "bbbbdacca"),
                new DiscretizedDataImpl(Optional.of(2), "bbabdadcd"),
                new DiscretizedDataImpl(Optional.of(2), "bccbdacca"),
                new DiscretizedDataImpl(Optional.of(2), "bbabdaaaa"),
                new DiscretizedDataImpl(Optional.of(3), "bbbcccbbb"),
                new DiscretizedDataImpl(Optional.of(3), "cbabbdccc"),
                new DiscretizedDataImpl(Optional.of(3), "abaacccca"),
                new DiscretizedDataImpl(Optional.of(3), "adddcacca")
        );
        Classifier classifier = new KNNClassifier(5, new LCSMeasure());
        ClassificationResult result = classifier.classify(test, train);
        assertEquals(1, result.getPredClass());
    }

    @Test
    public void testClassifyMethodWithValidInputs2() {
        List<DiscretizedData> train = Arrays.asList(
                new DiscretizedDataImpl(Optional.of(1), "abbdcabda"),
                new DiscretizedDataImpl(Optional.of(1), "abadcabdc"),
                new DiscretizedDataImpl(Optional.of(1), "aabdcacda"),
                new DiscretizedDataImpl(Optional.of(1), "accdcabdd"),
                new DiscretizedDataImpl(Optional.of(2), "bbbbdacca"),
                new DiscretizedDataImpl(Optional.of(2), "bbabdadcd"),
                new DiscretizedDataImpl(Optional.of(2), "bccbdacca"),
                new DiscretizedDataImpl(Optional.of(2), "bbabdaaaa"),
                new DiscretizedDataImpl(Optional.of(3), "bbbcccbbb"),
                new DiscretizedDataImpl(Optional.of(3), "cbabbdccc"),
                new DiscretizedDataImpl(Optional.of(3), "abaacccca"),
                new DiscretizedDataImpl(Optional.of(3), "adddcacca")
        );
        Classifier classifier = new KNNClassifier(3, new LCSMeasure());
        ClassificationResult result = classifier.classify(test, train);
        assertEquals(1, result.getPredClass());
    }
}
