import data.TimeSeries;
import data.TimeSeriesImpl;
import org.junit.Before;
import org.junit.Test;
import preprocessing.PreProcessor;
import preprocessing.SAXPreProcessor;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Testing class for the SaxPreProcessor class of the preprocessing package.
 * Created by George Shiangoli on 28/07/2016.
 */
public class SAXPreProcessorTest {

    private TimeSeries ts1, ts2, ts3;

    @Before
    public void init() {
        ts1 = new TimeSeriesImpl(1, Arrays.asList(1.4551,1.4196,1.3846,1.35,1.3161,1.259,1.2188,1.1786,1.1457,1.1055,1.0416,1.0015,0.9941,1.0274,1.0614,1.0555,1.0271,1.0222,1.0178,0.97316,0.94758,0.90391,0.90139,0.85855,0.85693,0.79413,0.75331,0.7125,0.67168,0.63086,0.57007,0.53054,0.49155,0.45313,0.41529,0.37807,0.34149,0.32833,0.29334,0.25906,0.26573,0.2328,0.2248,0.19369,0.1634,0.17344,0.14467,0.14221,0.11547,0.12829,0.10318,0.06731,0.044638,0.060469,0.039543,0.019667,-0.0081233,-0.025273,-0.005684,-0.020978,-0.035123,-0.054152,-0.065356,-0.075353,-0.084131,-0.09168,-0.12671,-0.13182,-0.13566,-0.13823,-0.16789,-0.16838,-0.19675,-0.22478,-0.22213,-0.24911,-0.24442,-0.26708,-0.29217,-0.31683,-0.3079,-0.33133,-0.3543,-0.37061,-0.39218,-0.41324,-0.43376,-0.44555,-0.46453,-0.48294,-0.50075,-0.51795,-0.52377,-0.57711,-0.55409,-0.56826,-0.58175,-0.59454,-0.59279,-0.6037,-0.61387,-0.6631,-0.65608,-0.70386,-0.71084,-0.71702,-0.72239,-0.72694,-0.75259,-0.75511,-0.75679,-0.75764,-0.75764,-0.73566,-0.77433,-0.77136,-0.76755,-0.80341,-0.79781,-0.80793,-0.80018,-0.8314,-0.8218,-0.8255,-0.85272,-0.83994,-0.86469,-0.85007,-0.84544,-0.8658,-0.84808,-0.8658,-0.84632,-0.79824,-0.74233,-0.68632,-0.65128,-0.59532,-0.55276,-0.5172,-0.48146,-0.44555,-0.42817,-0.39966,-0.37907,-0.35727,-0.33428,-0.34853,-0.29763,-0.259,-0.22031,-0.18154,-0.1299,-0.091207,-0.026656,0.025103,0.077096,0.090976,0.14351,0.15823,0.093773,0.055847,0.0028435,-0.035123,-0.099564,-0.12177,-0.14288,-0.16287,-0.18171,-0.17178,-0.18807,-0.20315,-0.1822,-0.19527,-0.21254,-0.22246,-0.23109,-0.23841,-0.21287,-0.18938,-0.16271,-0.13566,-0.10826,-0.080523,-0.11017,-0.19675,-0.25447,-0.28248,-0.31013,-0.36621,-0.39307,-0.41952,-0.47703,-0.53453,-0.5891,-0.64649,-0.70386,-0.73566,-0.76713,-0.82463,-0.85781,-0.88819,-0.91818,-0.94773,-0.94773,-0.94424,-0.91337,-0.9079,-0.90063,-0.89158,-0.85249,-0.84016,-0.86136,-0.84632,-0.85714,-0.83862,-0.85581,-0.83468,-0.85007,-0.86469,-0.83994,-0.83928,-0.85073,-0.8218,-0.87115,-0.86425,-0.83161,-0.83818,-0.84389,-0.84874,-0.81204,-0.79588,-0.7976,-0.79846,-0.75764,-0.75679,-0.73358,-0.73067,-0.72694,-0.72239,-0.69385,-0.64709,-0.59979,-0.59201,-0.54362,-0.49479,-0.42078,-0.37096,-0.36007,-0.30944,-0.2585,-0.19396,-0.14272,-0.10383,-0.052446,0.012101,0.050922,0.10244,0.1542,0.19284,0.25739,0.28329,0.34784,0.39979,0.43834,0.49039,0.55492,0.60715,0.64555,0.69785,0.7362,0.80073,0.8531,0.89142,0.94385,0.99644,1.0609,1.1137,1.1665,1.2045,1.2575,1.3105,1.3749,1.4281,1.4814,1.5347,1.5882,1.6313,1.6478,1.7018,1.7189,1.7732,1.7907,1.8358,1.854,1.8362,1.819,1.7945,1.7791,1.7995,1.8554,1.9113,1.9253,1.9121,1.8995,1.8652,1.8536,1.8144,1.7807,1.7472,1.7138,1.6806,1.6715,1.6918,1.7166,1.7094,1.7031,1.6714,1.6374,1.6062,1.5752,1.5714,1.5683,1.5371,1.5073,1.4496,1.4201,1.3624,1.2758,1.2464,1.1887,1.1309,1.0732,1.0151,0.95741,0.92888,0.87115,0.81343,0.75534,0.69761,0.63988,0.58214,0.52441,0.43781,0.38008,0.32235,0.26461,0.20688,0.12029,0.062553,0.033535,-0.0242,-0.081935,-0.13951,-0.19724,-0.25497,-0.3127,-0.37043,-0.45703,-0.48534,-0.54306,-0.60077,-0.62864,-0.68632,-0.74254,-0.80018,-0.85781,-0.8846,-0.9396,-0.96527,-1.0226,-1.0476,-1.0719,-1.1289,-1.1808,-1.2034,-1.2599,-1.2814,-1.3374,-1.3856,-1.4048,-1.4597,-1.4775,-1.5208,-1.536,-1.5885,-1.6016,-1.6526,-1.6634,-1.6969,-1.7444,-1.7503,-1.7546,-1.7979,-1.7988,-1.8171,-1.8135,-1.8485,-1.8565,-1.8461,-1.8727,-1.8584,-1.8795,-1.8612,-1.8485,-1.8598,-1.8354,-1.8099,-1.8135,-1.7851,-1.7546,-1.7495,-1.7171,-1.6842,-1.6732,-1.6385,-1.5957,-1.5598,-1.5416,-1.4775,-1.4403,-1.4187,-1.3807,-1.3425,-1.3042,-1.2397,-1.2012,-1.1754,-1.1364,-1.0974,-1.0328,-0.99371,-0.95449,-0.9152,-0.87585,-0.81139,-0.772,-0.73254,-0.69304,-0.64311,-0.57866,-0.53926,-0.48904,-0.44973,-0.41037,-0.3348,-0.29558,-0.24492,-0.19396,-0.14272,-0.091207,-0.026656,0.025103,0.077096,0.12931,0.16771,0.24624,0.28453,0.33702,0.37526,0.42782,0.49232,0.53054,0.58315,0.62132,0.65956,0.69785,0.76237,0.80073,0.83915,0.89015,0.92877,0.95459,0.99343,1.0323,1.0592,1.112,1.1401,1.1794,1.2082,1.2377,1.283,1.314,1.3456,1.3777,1.3702,1.4201,1.4137,1.448,1.4425,1.4778,1.4731,1.4464,1.4832,1.4799,1.4769,1.4743,1.4916,1.4492,1.448,1.4472,1.4468,1.4674,1.468,1.469,1.5112,1.5129,1.4959,1.4987,1.5018,1.5054,1.4687));
        ts2 = new TimeSeriesImpl(-1, Arrays.asList(2.02, 2.33, 2.99, 6.85, 9.20, 8.80, 7.50, 6.00, 5.85, 3.85, 4.85, 3.85, 2.22, 1.45, 1.34));
        ts3 = new TimeSeriesImpl(-1, Arrays.asList(0.50, 1.29, 2.58, 3.83, 3.25, 4.25, 3.83, 5.63, 6.44, 6.25, 8.75, 8.83, 3.25, 0.75, 0.72));
    }

    @Test (expected = IllegalArgumentException.class)
    public void SAXPreProcessorConstructorWithNegativeFrameCountFails() {
        new SAXPreProcessor(-1, 2);
    }

    @Test (expected = IllegalArgumentException.class)
    public void SAXPreProcessorConstructorWithZeroFrameCountFails() {
        new SAXPreProcessor(0, 2);
    }

    @Test (expected = IllegalArgumentException.class)
    public void SAXPreProcessorConstructorWithNegativeAlphabetSizeFails() {
        new SAXPreProcessor(1, -1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void SAXPreProcessorConstructorWithZeroAlphabetSizeFails() {
        new SAXPreProcessor(1, 0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void SAXPreProcessorConstructorWithSingleAlphabetSizeFails() {
        new SAXPreProcessor(1, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void SAXPreProcessorConstructorWithLargeAlphabetSizeFails() {
        new SAXPreProcessor(1, 27);
    }

    @Test (expected = NullPointerException.class)
    public void testDiscretizeWithNullArgumentFails() {
        PreProcessor processor = new SAXPreProcessor(1, 2);
        processor.discretize(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDiscretizeWithEmptyListFails() {
        PreProcessor processor = new SAXPreProcessor(1, 2);
        processor.discretize(new TimeSeriesImpl(-1, new ArrayList<>()));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDiscretizeWithListSizeSmallerThanFrameCountFails() {
        PreProcessor processor = new SAXPreProcessor(3, 2);
        processor.discretize(new TimeSeriesImpl(-1, Arrays.asList(1.1, 2.1)));
    }

    @Test
    public void testDiscretizeWithTimeSeriesSample1() {
        PreProcessor processor = new SAXPreProcessor(20, 5);
        String output = processor.discretize(ts1);
        assertEquals(20, output.length());
        assertEquals("edcbbbcbabdeedaaacee", output);
    }

    @Test
    public void testDiscretizeWithTimeSeriesSample2() {
        PreProcessor processor = new SAXPreProcessor(9, 4);
        String output = processor.discretize(ts2);
        assertEquals(9, output.length());
        assertEquals("abddccbaa", output);
    }

    @Test
    public void testDiscretizeWithTimeSeriesSample3() {
        PreProcessor processor = new SAXPreProcessor(9, 4);
        String output = processor.discretize(ts3);
        assertEquals(9, output.length());
        assertEquals("abbccddba", output);
    }

}
