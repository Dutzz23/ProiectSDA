package org.example.og;

class RBTreeNode {
    enum Color { RED, BLACK }

    int key;
    Color color;
    RBTreeNode left, right, parent;

    RBTreeNode(int key) {
        this.key = key;
        this.color = Color.RED;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}
