package model.data;

/**
 * This class represents the range of time series lengths that
 * a given input file can hold.
 * Created by George Shiangoli on 19/07/2016.
 */
public class DataLengthRange {

    private long lowerBound;
    private long upperBound;

    public DataLengthRange(long lowerBound, long upperBound) {
        setLowerBound(lowerBound);
        setUpperBound(upperBound);
    }

    public long getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(long lowerBound) {
        this.lowerBound = lowerBound;
    }

    public long getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(long upperBound) {
        this.upperBound = upperBound;
    }

}
