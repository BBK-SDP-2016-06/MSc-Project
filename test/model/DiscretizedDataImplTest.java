package model;

import classificationApp.model.data.DiscretizedData;
import classificationApp.model.data.DiscretizedDataImpl;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Testing class to assert behaviour of DiscretizedData classificationApp.model.data structure.
 * Created by George Shiangoli on 28/07/2016.
 */
public class DiscretizedDataImplTest {

    @Test
    public void testGetClassType() {
        for (int i = 1; i <= 10; i++) {
            DiscretizedData data = new DiscretizedDataImpl(i, "abcd");
            assertEquals(i, data.getClassType());
        }
    }

    @Test
    public void testGetWord() {
        DiscretizedData data = new DiscretizedDataImpl(1, "abcd");
        assertEquals("abcd", data.getWord());
    }

    @Test
    public void minusOneClassTypeDoesNotFail() {
        new DiscretizedDataImpl(-1, "abcd");
    }

    @Test (expected = IllegalArgumentException.class)
    public void negativeClassTypeFails() {
        new DiscretizedDataImpl(-2, "abcd");
    }

    @Test (expected = IllegalArgumentException.class)
    public void nullWordFails() {
        new DiscretizedDataImpl(-2, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void emptyWordFails() {
        new DiscretizedDataImpl(-2, "");
    }
}
