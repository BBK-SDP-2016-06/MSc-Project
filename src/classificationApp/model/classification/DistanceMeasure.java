package classificationApp.model.classification;

/**
 * A class that represents the group of algorithms charged
 * with assessing the similarity between two Strings obtained
 * from a DataApproximator.
 * Created by Shiangoli George on 22/07/2016.
 */
public interface DistanceMeasure {

    /**
     * Returns a measure of how similar the two string arguments are to each other.
     * A higher similarity factor indicates a stronger equivalence between the
     * arguments.
     * @param word1 first string.
     * @param word2 second string.
     * @return an integer representing a measure of similarity between the two
     * strings.
     */
    int getSimilarityFactor(String word1, String word2);
}
