import org.example.Document;
import org.example.DocumentRBTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

public class DocumentRBTreeTest {
    private DocumentRBTree rbTree;

    @BeforeEach
    void setUp() {
        rbTree = new DocumentRBTree();
    }

    @Test
    void testInsert() {
        rbTree.insert(new Date(), new ArrayList<Document>(), "sdasd");
    }
}
