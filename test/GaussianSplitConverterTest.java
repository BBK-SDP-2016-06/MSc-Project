import org.junit.Test;
import preprocessing.GaussianSplitConverter;

/**
 * Tests for the GaussianSplitConverter class of the preprocessing package.
 * Created by George Shiangoli on 27/07/2016.
 */
public class GaussianSplitConverterTest {

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorWithAlphabetSizeOfOneFails() {
        new GaussianSplitConverter(1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorWithAlphabetSizeOfZeroFails() {
        new GaussianSplitConverter(0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeAlphabetSizeFails() {
        new GaussianSplitConverter(-1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorWithAlphabetSizeGreaterThan26Fails() {
        new GaussianSplitConverter(27);
    }

}
