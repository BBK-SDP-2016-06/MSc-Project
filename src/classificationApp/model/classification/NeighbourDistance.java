package classificationApp.model.classification;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Holds information about a training data sample's class type
 * and its computed distance from the test sample.
 * Created by George Shiangoli on 30/07/2016.
 */

@AllArgsConstructor
@Getter
public class NeighbourDistance {
    private int classType, distance;
}
