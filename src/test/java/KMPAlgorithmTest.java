import org.example.KMPAlgorithm;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class KMPAlgorithmTest {

    @Test
    void testGeneratePiTable() {
        int[] piTable = KMPAlgorithm.generatePiTable("pattern");
        assertNotNull(piTable);
        // Assertions to validate the pi table
    }

    @Test
    void testKMP() {
        String text = "sample text";
        String pattern = "text";
        int[] piTable = KMPAlgorithm.generatePiTable(pattern);
        assertTrue(KMPAlgorithm.KMP(text, pattern, piTable));
        // More test cases
    }
}
