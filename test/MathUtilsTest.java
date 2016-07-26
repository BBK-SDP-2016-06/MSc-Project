import org.junit.Test;
import preprocessing.MathUtils;
import java.util.Collections;
import static org.junit.Assert.*;

/**
 * Tests for the MathUtils class of the preprocessing package.
 * Created by George Shiangoli on 26/07/2016.
 */
public class MathUtilsTest {

    @Test
    public void testGetMeanOnSingleValue() {
        assertEquals(5.0, MathUtils.getMean(Collections.singletonList(5.0)), 0.0001);
    }
}
