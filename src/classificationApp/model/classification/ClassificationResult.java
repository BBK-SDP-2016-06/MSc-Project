package classificationApp.model.classification;

import java.util.List;

/**
 * A wrapper class that enables both a predicted class type
 * and corresponding confidence measure to be returned from
 * a classificationApp.model.classification process.
 * Created by George on 30/07/2016.
 */
public class ClassificationResult {

    private int classType;
    private List<NeighbourDistance> neighbourDistances;

    public ClassificationResult(int classType, List<NeighbourDistance> neighbourDistances) {
        setClassType(classType);
        setNeighbourDistances(neighbourDistances);
    }

    public int getClassType() {
        return classType;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }

    public List<NeighbourDistance> getNeighbourDistances() {
        return neighbourDistances;
    }

    public void setNeighbourDistances(List<NeighbourDistance> neighbourDistances) {
        this.neighbourDistances = neighbourDistances;
    }
}
