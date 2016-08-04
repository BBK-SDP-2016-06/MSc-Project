package classificationApp.model.classification;

/**
 * A wrapper class that enables both a predicted class type
 * and corresponding confidence measure to be returned from
 * a classificationApp.model.classification process.
 * Created by George on 30/07/2016.
 */
public class ClassificationResult {

    private int classType;
    private double confidence;

    public ClassificationResult(int classType, double confidence) {
        setClassType(classType);
        setConfidence(confidence);
    }

    public int getClassType() {
        return classType;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}
