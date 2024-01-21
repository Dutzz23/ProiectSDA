import org.example.Document;
import org.example.DocumentBTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DocumentBTreeTest {

    private DocumentBTree bTree;

    @BeforeEach
    void setUp() {
        bTree = new DocumentBTree(3);
    }

    @Test
    void testInsert() {
        Document doc = new Document("Title", "Author", 2020, "Abstract", true, "http://link.com");
        bTree.insert(doc);
    }

    @Test
    void testSearch() {
        Document doc = bTree.search("Title");
        assertEquals("Title", doc.title);
    }
}
