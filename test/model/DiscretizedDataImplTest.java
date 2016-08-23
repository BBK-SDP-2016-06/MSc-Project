package model;

import classificationApp.model.data.DiscretizedData;
import classificationApp.model.data.DiscretizedDataImpl;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Testing class to assert behaviour of DiscretizedData classificationApp.model.data structure.
 * Created by George Shiangoli on 28/07/2016.
 */
public class DiscretizedDataImplTest {

    @Test
    public void testGetClassType() {
        for (int i = 0; i <= 10; i++) {
            DiscretizedData data = new DiscretizedDataImpl(Optional.of(i), "abcd");
            assertTrue(data.getClassType().isPresent());
            assertEquals(i, (int)data.getClassType().get());
        }
    }

    @Test
    public void testGetWord() {
        DiscretizedData data = new DiscretizedDataImpl(Optional.of(1), "abcd");
        assertEquals("abcd", data.getWord());
    }

    @Test
    public void negativeClassTypeDoesNotFail() {
        new DiscretizedDataImpl(Optional.of(-1), "abcd");
    }

    @Test (expected = IllegalArgumentException.class)
    public void nullWordFails() {
        new DiscretizedDataImpl(Optional.of(1), null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void emptyWordFails() {
        new DiscretizedDataImpl(Optional.of(1), "");
    }
}
