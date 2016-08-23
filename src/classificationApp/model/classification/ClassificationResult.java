package classificationApp.model.classification;

import java.util.List;
import java.util.Optional;

/**
 * A wrapper class that enables both a predicted class type
 * and corresponding confidence measure to be returned from
 * a classificationApp.model.classification process.
 * Created by George on 30/07/2016.
 */
public class ClassificationResult {

    private int index;
    private Optional<Integer> actClass;
    private int predClass;
    private List<NeighbourDistance> neighbourDistances;
    private boolean correctPred;

    public ClassificationResult(Optional<Integer> actClass, int predClass, List<NeighbourDistance> neighbourDistances) {
        setActClass(actClass);
        setPredClass(predClass);
        setNeighbourDistances(neighbourDistances);
        setCorrectPred();
    }

    public Optional<Integer> getActClass() {
        return actClass;
    }

    public void setActClass(Optional<Integer> actClass) {
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
        correctPred = getActClass().isPresent() && getActClass().get() == getPredClass();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Index: ").append(index);
        sb.append("\n").append(neighbourDistances.size()).append("NN result:");
        sb.append("\n\n" + "Rank\t\tClass\tSimilarity");
        for (NeighbourDistance nd : neighbourDistances) {
            sb.append("\n")
                    .append(neighbourDistances.indexOf(nd) + 1)
                    .append("\t\t")
                    .append(nd.getClassType())
                    .append("\t\t")
                    .append(nd.getDistance());
        }
        sb.append("\n\nActual class: ").append(actClass.isPresent()? actClass.get() : "Unlabelled");
        sb.append("\nPredicted class: ").append(predClass);

        if (actClass.isPresent()) {
            sb.append(isCorrectPred() ? "\nCORRECT" : "\nINCORRECT");
        } else {
            sb.append("\nUNLABELLED");
        }
        sb.append("\n--------------------------------------\n");
        return sb.toString();
    }
 }
