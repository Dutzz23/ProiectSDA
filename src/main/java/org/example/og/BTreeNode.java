package org.example.og;

class BTreeNode {
    int[] keys;
    int t;
    BTreeNode[] children;
    int n;
    boolean leaf;

    public BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        this.keys = new int[2 * t - 1];
        this.children = new BTreeNode[2 * t];
        this.n = 0;
    }

    public BTreeNode search(int key) {
        int i = 0;
        while (i < n && key > keys[i])
            i++;

        if (i < n && keys[i] == key)
            return this;

        if (leaf)
            return null;

        return children[i].search(key);
    }

    public void traverse() {
        int i;
        for (i = 0; i < n; i++) {
            if (!leaf)
                children[i].traverse();
            System.out.print(keys[i] + " ");
        }

        if (!leaf)
            children[i].traverse();
    }

    void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + keys[0]);
        for (int i = 1; i < n; i++) {
            System.out.println(prefix + (isTail ? "    " : "│   ") + keys[i]);
        }
        for (int i = 0; i < n + 1; i++) {
            if (children[i] != null) {
                children[i].print(prefix + (isTail ? "    " : "│   "), i == n);
            }
        }
    }
}
