import org.example.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
class DocumentTest {

    @Test
    void testDocumentConstructor() {
        Document doc = new Document("Title", "Author", 2020, "Abstract", true, "http://link.com");
        assertNotNull(doc);
    }
}
