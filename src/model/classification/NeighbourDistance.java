package model.classification;

/**
 * Holds information about a training model.data sample's class type
 * and its computed distance from the test sample.
 * Created by George Shiangoli on 30/07/2016.
 */
public class NeighbourDistance {

    private int classType, distance;

    public NeighbourDistance(int classType, int distance) {
        setClassType(classType);
        setDistance(distance);
    }

    public int getClassType() {
        return classType;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return classType + "-" + distance;
    }
}
