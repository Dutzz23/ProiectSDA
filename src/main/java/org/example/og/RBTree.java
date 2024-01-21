package org.example.og;

class RBTree {
    private RBTreeNode root;

    public RBTree() {
        root = null;
    }

    // Method to rotate node left
    private void leftRotate(RBTreeNode x) {
        RBTreeNode y = x.right;
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

    // Method to rotate node right
    private void rightRotate(RBTreeNode x) {
        RBTreeNode y = x.left;
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

    // Method to fix violations after insert
    private void fixInsert(RBTreeNode k) {
        RBTreeNode u;
        while (k.parent.color == RBTreeNode.Color.RED) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u.color == RBTreeNode.Color.RED) {
                    u.color = RBTreeNode.Color.BLACK;
                    k.parent.color = RBTreeNode.Color.BLACK;
                    k.parent.parent.color = RBTreeNode.Color.RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = RBTreeNode.Color.BLACK;
                    k.parent.parent.color = RBTreeNode.Color.RED;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;
                if (u.color == RBTreeNode.Color.RED) {
                    u.color = RBTreeNode.Color.BLACK;
                    k.parent.color = RBTreeNode.Color.BLACK;
                    k.parent.parent.color = RBTreeNode.Color.RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = RBTreeNode.Color.BLACK;
                    k.parent.parent.color = RBTreeNode.Color.RED;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = RBTreeNode.Color.BLACK;
    }


    // Method to insert a new node
    public void insert(int key) {
        RBTreeNode node = new RBTreeNode(key);
        RBTreeNode y = null;
        RBTreeNode x = this.root;

        while (x != null) {
            y = x;
            if (node.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.key < y.key) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.color = RBTreeNode.Color.BLACK;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        fixInsert(node);
    }



    // Additional methods such as delete, search, etc. can be added here

    // Method to search for a key in the tree
    public RBTreeNode search(int key) {
        return searchRecursive(this.root, key);
    }

    // Recursive helper method for searching
    private RBTreeNode searchRecursive(RBTreeNode node, int key) {
        // Base case: root is null or key is present at root
        if (node == null || node.key == key) {
            return node;
        }

        // Key is greater than root's key, search in the right subtree
        if (key > node.key) {
            return searchRecursive(node.right, key);
        }

        // Key is smaller than root's key, search in the left subtree
        return searchRecursive(node.left, key);
    }

    public void print() {
        printHelper(this.root, "", true);
    }

    // Recursive method to print the tree structure
    private void printHelper(RBTreeNode node, String indent, boolean last) {
        if (node != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }
            String color = node.color == RBTreeNode.Color.RED ? "RED" : "BLACK";
            System.out.println(node.key + "(" + color + ")");
            printHelper(node.left, indent, false);
            printHelper(node.right, indent, true);
        }
    }
}

