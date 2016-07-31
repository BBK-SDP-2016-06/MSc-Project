package model.classification;

/**
 * Uses the Longest Common Subsequence Algorithm as a similarity measure.
 * Created by George Shiangoli on 29/07/2016.
 */
public class LCSMeasure implements DistanceMeasure {

    /**
     * Calculates the length of the Longest Common Subsequence of the two input
     * strings. The application uses this length as a similarity factor between
     * two time series approximations.
     * @param word1 first string.
     * @param word2 second string.
     * @return the similarity factor of the two input strings as the length of their
     * longest common subsequence.
     */
    @Override
    public int getSimilarityFactor(String word1, String word2) {
        return (word1 == null || word2 == null) ? 0 : dynamicProgrammingSolution(word1, word2);
    }

    private int dynamicProgrammingSolution(String word1, String word2) {
        String s1 = "$" + word1;
        String s2 = "$" + word2;

        int xLength = s1.length();
        int yLength = s2.length();

        int[][] matrix = new int[xLength][yLength];

        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {

                if (s1.charAt(i) == '$' || s2.charAt(j) == '$') {
                    matrix[i][j] = 0;
                } else if (s1.charAt(i) == s2.charAt(j)) {
                    matrix[i][j] = 1 + matrix[i - 1][j - 1];
                } else {
                    matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
                }
            }
        }

        return matrix[xLength - 1][yLength - 1];
    }

    /*
    private int recursiveSolution(String word1, String word2) {
        if (word1.isEmpty() || word2.isEmpty()) {
            return 0;
        }
        else if (word1.charAt(0) == word2.charAt(0)) {
            return 1 + recursiveSolution(word1.substring(1), word2.substring(1));
        }
        else {
            return Math.max(recursiveSolution(word1.substring(1), word2),
                    recursiveSolution(word1, word2.substring(1)));
        }
    }
    */

}
