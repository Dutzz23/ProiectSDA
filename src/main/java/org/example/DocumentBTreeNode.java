package org.example;

import java.util.ArrayList;
import java.util.List;

public class DocumentBTreeNode {
    List<Document> documents;
    int t;
    List<DocumentBTreeNode> children;
    int n;
    boolean leaf;


    public DocumentBTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        this.documents = new ArrayList<>(2 * t - 1);
        this.children = new ArrayList<>(2 * t);
        this.n = 0;
    }
}

