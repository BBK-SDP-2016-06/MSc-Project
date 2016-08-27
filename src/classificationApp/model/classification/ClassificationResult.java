package classificationApp.model.classification;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

/**
 * A wrapper class that enables both a predicted class type
 * and corresponding confidence measure to be returned from
 * a classificationApp.model.classification process.
 * Created by George on 30/07/2016.
 */
@Getter
@Setter
public class ClassificationResult {

    private int index;
    private Optional<Integer> actClass;
    private int predictedClass;
    private List<NeighbourDistance> neighbourDistances;
    private boolean correctPrediction;

    public ClassificationResult(Optional<Integer> actClass, int predClass, List<NeighbourDistance> neighbourDistances) {
        setActClass(actClass);
        setPredictedClass(predClass);
        setNeighbourDistances(neighbourDistances);
        setCorrectPrediction();
    }

    public void setCorrectPrediction() {
        correctPrediction = getActClass().isPresent() && getActClass().get() == getPredictedClass();
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
        sb.append("\nPredicted class: ").append(predictedClass);

        if (actClass.isPresent()) {
            sb.append(isCorrectPrediction() ? "\nCORRECT" : "\nINCORRECT");
        } else {
            sb.append("\nUNLABELLED");
        }
        sb.append("\n--------------------------------------\n");
        return sb.toString();
    }
 }
