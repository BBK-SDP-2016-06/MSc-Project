package classificationApp.model.classification;

import lombok.*;

/**
 * Holds information about a training data sample's class type
 * and its computed distance from the test sample.
 * Created by George Shiangoli on 30/07/2016.
 */
@Data
@AllArgsConstructor
public class NeighbourDistance {

    private int classType, distance;

}
