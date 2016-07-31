package classification;

import data.DiscretizedData;
import preprocessing.MathUtils;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

/**
 * Classifies a given test sample against a training set via means
 * of the k nearest neighbour classification algorithm. The k value
 * indicates the number of closest samples that will be assessed and
 * the distance between the test and training samples is calculated
 * by a DistanceMeasurer.
 * Created by George Shiangoli on 30/07/2016.
 */
public class KNNClassifier implements Classifier {

    private int kValue;
    private DistanceMeasure distanceMeasure;

    public KNNClassifier(int kValue, DistanceMeasure distanceMeasure) {
        this.kValue = kValue;
        this.distanceMeasure = distanceMeasure;
    }

    @Override
    public ClassificationResult classify(DiscretizedData test, List<DiscretizedData> train) {

        List<NeighbourDistance> neighbourDistances = train.parallelStream()
                .map(data -> new NeighbourDistance(data.getClassType(), distanceMeasure.getSimilarityFactor(test.getWord(), data.getWord())))
                .collect(toList());

        List<Integer> closestClassTypes = neighbourDistances.parallelStream()
                .sorted((n1, n2) -> n2.getDistance() - n1.getDistance())
                .limit(kValue)
                .map(NeighbourDistance::getClassType)
                .collect(toList());

        Map<Integer, Long> classTypeCount = closestClassTypes.parallelStream().collect(groupingBy(i -> i, counting()));

        int predictedClass = classTypeCount.keySet().parallelStream()
                .filter(c -> classTypeCount.get(c) == classTypeCount.values().parallelStream().mapToLong(l -> l).max().getAsLong())
                .findFirst().get();

        double confidence = MathUtils.to5SF(classTypeCount.get(predictedClass).doubleValue() / (double) kValue);

        return new ClassificationResult(predictedClass, confidence);
    }
}
