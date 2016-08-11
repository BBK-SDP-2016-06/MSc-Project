package classificationApp.model.classification;

import java.util.List;

/**
 * A wrapper class that enables both a predicted class type
 * and corresponding confidence measure to be returned from
 * a classificationApp.model.classification process.
 * Created by George on 30/07/2016.
 */
public class ClassificationResult {

    private int actClass;
    private int predClass;
    private List<NeighbourDistance> neighbourDistances;
    private boolean correctPred;

    public ClassificationResult(int actClass, int predClass, List<NeighbourDistance> neighbourDistances) {
        setActClass(actClass);
        setPredClass(predClass);
        setNeighbourDistances(neighbourDistances);
        setCorrectPred();
    }

    public int getActClass() {
        return actClass;
    }

    public void setActClass(int actClass) {
        this.actClass = actClass;
    }

    public int getPredClass() {
        return predClass;
    }

    public void setPredClass(int predClass) {
        this.predClass = predClass;
    }

    public List<NeighbourDistance> getNeighbourDistances() {
        return neighbourDistances;
    }

    public void setNeighbourDistances(List<NeighbourDistance> neighbourDistances) {
        this.neighbourDistances = neighbourDistances;
    }

    public boolean isCorrectPred() {
        return correctPred;
    }

    public void setCorrectPred() {
        correctPred = getActClass() == getPredClass();
    }
}
