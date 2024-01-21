package org.example;

import java.util.Date;
import java.util.List;

/**
 * Represents a Red-Black tree data structure for storing and managing documents.
 */
public class DocumentRBTree {
    private DocumentRBTreeNode root;

    /**
     * Constructor
     */
    public DocumentRBTree() {
        root = null;
    }

    /**
     * Root getter
     * @return this.root
     */
    public DocumentRBTreeNode getRoot() {
        return this.root;
    }

    /**
     * Inserts a new entry into the Red-Black tree.
     * @param key The date key associated with the entry.
     * @param data The list of documents to be associated with the key.
     * @param searchedValue The search value associated with the entry.
     */
    public void insert(Date key, List<Document> data, String searchedValue) {
        DocumentRBTreeNode node = new DocumentRBTreeNode(key, data, searchedValue);
        DocumentRBTreeNode y = null;
        DocumentRBTreeNode x = this.root;

        while (x != null) {
            y = x;
            if (node.key.before(x.key)) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.key.before(y.key)) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.color = DocumentRBTreeNode.Color.BLACK;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        fixInsert(node);
    }

    private void leftRotate(DocumentRBTreeNode x) {
        DocumentRBTreeNode y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(DocumentRBTreeNode x) {
        DocumentRBTreeNode y = x.left;
        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    private void fixInsert(DocumentRBTreeNode k) {
        DocumentRBTreeNode u;
        while (k.parent != null && k.parent.color == DocumentRBTreeNode.Color.RED) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u != null && u.color == DocumentRBTreeNode.Color.RED) {
                    u.color = DocumentRBTreeNode.Color.BLACK;
                    k.parent.color = DocumentRBTreeNode.Color.BLACK;
                    k.parent.parent.color = DocumentRBTreeNode.Color.RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = DocumentRBTreeNode.Color.BLACK;
                    k.parent.parent.color = DocumentRBTreeNode.Color.RED;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;
                if (u != null && u.color == DocumentRBTreeNode.Color.RED) {
                    u.color = DocumentRBTreeNode.Color.BLACK;
                    k.parent.color = DocumentRBTreeNode.Color.BLACK;
                    k.parent.parent.color = DocumentRBTreeNode.Color.RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = DocumentRBTreeNode.Color.BLACK;
                    k.parent.parent.color = DocumentRBTreeNode.Color.RED;
                    rightRotate(k.parent.parent);
                }
            }
        }
        root.color = DocumentRBTreeNode.Color.BLACK;
    }
}
