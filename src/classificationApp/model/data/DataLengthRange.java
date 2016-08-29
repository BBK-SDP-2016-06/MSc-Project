package classificationApp.model.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the range of time series lengths that
 * a given input file can hold.
 * Created by George Shiangoli on 19/07/2016.
 */
@AllArgsConstructor
@Getter
@Setter
public class DataLengthRange {

    private long lowerBound;
    private long upperBound;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(lowerBound);
        if (lowerBound == upperBound) return sb.toString();
        else return sb.append(" - ").append(upperBound).toString();
    }

}
