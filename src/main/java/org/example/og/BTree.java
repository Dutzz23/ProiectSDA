package org.example.og;

public class BTree {
    private BTreeNode root;
    private int t;

    public BTree(int t) {
        this.root = null;
        this.t = t;
    }

    // Insert key into the tree
    public void insert(int key) {
        // If root is null, create a new node
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = key;
            root.n = 1;
        } else {
            // If root is full, then the tree grows in height
            if (root.n == 2 * t - 1) {
                BTreeNode newRoot = new BTreeNode(t, false);
                newRoot.children[0] = root;
                split(newRoot, 0, root);
                root = newRoot;
            }
            insertNonFull(root, key);
        }
    }

    // Split the child of a node
    private void split(BTreeNode parent, int i, BTreeNode child) {
        BTreeNode newChild = new BTreeNode(child.t, child.leaf);
        newChild.n = t - 1;

        for (int j = 0; j < t - 1; j++) {
            newChild.keys[j] = child.keys[j + t];
        }

        if (!child.leaf) {
            for (int j = 0; j < t; j++) {
                newChild.children[j] = child.children[j + t];
            }
        }

        child.n = t - 1;

        for (int j = parent.n; j >= i + 1; j--) {
            parent.children[j + 1] = parent.children[j];
        }

        parent.children[i + 1] = newChild;

        for (int j = parent.n - 1; j >= i; j--) {
            parent.keys[j + 1] = parent.keys[j];
        }

        parent.keys[i] = child.keys[t - 1];
        parent.n = parent.n + 1;
    }


    // Insert a key into a non-full node
    private void insertNonFull(BTreeNode node, int key) {
        int i = node.n - 1;
        // If it's a leaf, is searching the position to insert the new key in an ordered array
        if (node.leaf) {
            while (i >= 0 && node.keys[i] > key) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.n = node.n + 1;
        } else {
            while (i >= 0 && node.keys[i] > key) {
                i--;
            }
            if (node.children[i + 1].n == 2 * t - 1) {
                split(node, i + 1, node.children[i + 1]);
                if (key > node.keys[i + 1]) {
                    i++;
                }
            }
            insertNonFull(node.children[i + 1], key);
        }
    }

    // Function to search a key in this tree
    public BTreeNode search(int key) {
        return (root == null) ? null : root.search(key);
    }

    // Function to traverse the tree
    public void traverse() {
        if (root != null)
            root.traverse();
    }

    public void print() {
        if (root != null) {
            root.print("", true);
        }
    }
}

