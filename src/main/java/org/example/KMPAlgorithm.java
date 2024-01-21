package org.example;

/**
 * Implements the Knuth-Morris-Pratt algorithm for pattern searching in strings.
 */
public class KMPAlgorithm {

    public static void main(String[] args) {
        String text = "abaabaadadadabaaba";
        String pattern = "abaaba";
        boolean isFound = KMP(text, pattern, generatePiTable(pattern));
    }

    /**
     * Executes the KMP pattern searching algorithm.
     *
     * @param text    The text to be searched.
     * @param pattern The pattern to search for in the text.
     * @param Pi      The pi table for the pattern.
     * @return True if the pattern is found in the text, false otherwise.
     */
    public static boolean KMP(String text, String pattern, int[] Pi) {
        int patternSize = pattern.length();
        int textSize = text.length();
        int q = 0;

        for (int i = 0; i < textSize; i++) {
            while (q > 0 && pattern.charAt(q) != text.charAt(i)) {
                q = Pi[q - 1];
            }
            if (pattern.charAt(q) == text.charAt(i)) {
                q++;
            }
            if (q == patternSize) {
//                System.out.println("Pattern found at position " + (i - patternSize + 1));
//                q = Pi[q - 1];
                return true;
            }
        }
        return false;
    }

    /**
     * Generates the partial match table (also known as "pi table") for the KMP algorithm.
     *
     * @param pattern The pattern for which the table is to be created.
     * @return The pi table as an array of integers.
     */
    public static int[] generatePiTable(String pattern) {
        int patternSize = pattern.length();
        int[] Pi = new int[patternSize];
        Pi[0] = 0;
        int k = 0;

        for (int q = 1; q < patternSize; q++) {
            while (k > 0 && pattern.charAt(k) != pattern.charAt(q)) {
                k = Pi[k - 1];
            }
            if (pattern.charAt(k) == pattern.charAt(q)) {
                k++;
            }
            Pi[q] = k;
        }
        return Pi;
    }
}