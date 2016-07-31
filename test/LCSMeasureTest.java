import model.classification.DistanceMeasure;
import model.classification.LCSMeasure;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the LCSMeasure class of the model.classification package.
 * Created by George Shiangoli on 30/07/2016.
 */
public class LCSMeasureTest {

    private DistanceMeasure measure;

    @Before
    public void init() {
        measure = new LCSMeasure();
    }

    @Test
    public void testGetSimilarityFactorOnEmptyStrings() {
        assertEquals(0, measure.getSimilarityFactor("", ""));
    }

    @Test
    public void testGetSimilarityFactorOnNullStrings() {
        assertEquals(0, measure.getSimilarityFactor(null, ""));
        assertEquals(0, measure.getSimilarityFactor("", null));
        assertEquals(0, measure.getSimilarityFactor(null, null));
    }

    @Test
    public void testGetSimilarityMeasureOnOneEmptyString() {
        assertEquals(0, measure.getSimilarityFactor("abc", ""));
        assertEquals(0, measure.getSimilarityFactor("", "abc"));
    }

    @Test
    public void testGetSimilarityMeasureOnEqualStrings() {
        assertEquals(1, measure.getSimilarityFactor("a", "a"));
        assertEquals(2, measure.getSimilarityFactor("ab", "ab"));
        assertEquals(3, measure.getSimilarityFactor("abc", "abc"));
        assertEquals(4, measure.getSimilarityFactor("abca", "abca"));
    }

    @Test
    public void testGetSimilarityMeasureOnName() {
        assertEquals(2, measure.getSimilarityFactor("george", "shiangoli"));
    }

    @Test
    public void testGetSimilarityMeasureOnName2() {
        assertEquals(2, measure.getSimilarityFactor("savvas", "shiangoli"));
    }

    @Test
    public void testGetSimilarityMeasureOnExample1() {
        assertEquals(4, measure.getSimilarityFactor("abcda", "acbdea"));
    }

    @Test
    public void testGetSimilarityMeasureOnExample2() {
        assertEquals(4, measure.getSimilarityFactor("abcdaf", "acbcf"));
    }


    @Test
    public void testGetSimilarityMeasureOnInternetExample() {
        assertEquals(7, measure.getSimilarityFactor("nematode_knowledge", "empty_bottle"));
    }

    @Test (timeout = 500)
    public void testGetSimilarityMeasureOnLargeStrings() {
        measure.getSimilarityFactor("alkajsdfhalsjdfhlaskjdfhalskjfhaslaaabfgyhtr",
                                    "sdnbscydfjksafhdliudsfhksjdfhlkasjdfhlakdfhasdfg");
    }

    @Test (timeout = 500)
    public void testGetSimilarityMeasureOnHugeStrings() {
        measure.getSimilarityFactor("ACAAGATGCCATTGTCCCCCGGCCTCCTGCTGCTGCTGCTCTCCGGGGCCACGGCCACCGCTGCCCTGCCCCTGGAGGGTGGCCCCACCGGCCGAGACAGCGAGCATATGCAGGAAGCGGCAGGAATAAGGAAAAGCAGC",
                        "CTCCTGACTTTCCTCGCTTGGTGGTTTGAGTGGACCTCCCAGGCCAGTGCCGGGCCCCTCATAGGAGAGGAAGCTCGGGAGGTGGCCAGGCGGCAGGAAGGCGCACCCCCCCAGCAATCCGCGCGCCGGGACAGAATGCC");
    }
}
