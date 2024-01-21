package org.example;

import java.util.Date;
import java.util.List;

/**
 * Represents a node in the Document Red-Black tree.
 */
class DocumentRBTreeNode {
    enum Color {RED, BLACK}

    Date key;
    List<Document> data;
    Color color;
    DocumentRBTreeNode left, right, parent;
    String searchedValue;

    /**
     * Constructs a new node for the Document Red-Black tree.
     * @param key The date key associated with the node.
     * @param data The list of documents associated with the node.
     * @param searchedValue The search value associated with the node.
     */
    DocumentRBTreeNode(Date key, List<Document> data,String searchedValue) {
        this.key = key;
        this.data = data;
        this.color = Color.RED;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.searchedValue = searchedValue;
    }
}
