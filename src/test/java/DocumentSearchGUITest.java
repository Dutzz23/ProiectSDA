import org.example.DocumentSearchGUI;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DocumentSearchGUITest {
    @Test
    public void testGuiCreation() {
        DocumentSearchGUI gui = new DocumentSearchGUI();
        assertNotNull(gui);
    }
}
