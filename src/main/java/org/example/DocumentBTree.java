package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a B-tree data structure for storing and managing documents.
 */
public class DocumentBTree {
    private DocumentBTreeNode root;
    private int t;

    /**
     * Initializes a new instance of the DocumentBTree class.
     *
     * @param t The minimum degree of the B-tree (each node other than root must have at least t-1 keys and at most 2t-1 keys).
     */
    public DocumentBTree(int t) {
        this.root = null;
        this.t = t;
    }


    // Methods:

    /**
     * Inserts a new document into the B-tree.
     *
     * @param doc The document to be inserted.
     */
    public void insert(Document doc) {
        if (root == null) {
            root = new DocumentBTreeNode(t, true);
            root.documents.add(doc);
            root.n = 1;
        } else {
            if (root.n == 2 * t - 1) {
                DocumentBTreeNode newRoot = new DocumentBTreeNode(t, false);
                newRoot.children.add(root);
                split(newRoot, 0, root);
                root = newRoot;
                insertNonFull(root, doc);
            } else {
                insertNonFull(root, doc);
            }
        }
    }

    /**
     * Inserts a document into a non-full node.
     *
     * @param node The node to insert the document into.
     * @param doc  The document to insert.
     */
    private void insertNonFull(DocumentBTreeNode node, Document doc) {
        int i = node.n - 1;

        if (node.leaf) {
            while (i >= 0 && node.documents.get(i).title.compareTo(doc.title) > 0) {
                i--;
            }
            node.documents.add(i + 1, doc);
            node.n++;
        } else {
            while (i >= 0 && node.documents.get(i).title.compareTo(doc.title) > 0) {
                i--;
            }
            i++;
            if (node.children.get(i).n == 2 * t - 1) {
                split(node, i, node.children.get(i));
                if (doc.title.compareTo(node.documents.get(i).title) > 0) {
                    i++;
                }
            }
            insertNonFull(node.children.get(i), doc);
        }
    }

    /**
     * Splits the child node of the given parent node.
     *
     * @param parent The parent node containing the child to be split.
     * @param i      The index of the child in the parent node.
     * @param child  The child node to be split.
     */
    private void split(DocumentBTreeNode parent, int i, DocumentBTreeNode child) {
        DocumentBTreeNode newNode = new DocumentBTreeNode(t, child.leaf);
        newNode.n = t - 1;

        // Moving documents to the new node
        for (int j = 0; j < t - 1; j++) {
            newNode.documents.add(child.documents.remove(t));
        }

        // Moving children if not a leaf
        if (!child.leaf) {
            for (int j = 0; j < t; j++) {
                newNode.children.add(child.children.remove(t));
            }
        }

        child.n = t - 1;
        parent.children.add(i + 1, newNode);
        parent.documents.add(i, child.documents.remove(t - 1));
        parent.n++;
    }

    /**
     * Searches for a document in the B-tree by its title.
     *
     * @param title The title of the document to search for.
     * @return The DocumentBTreeNode containing the document if found, otherwise null.
     */
    public Document search(String title) {
        return searchRecursive(root, title);
    }

    /**
     * Recursively searches for a document in the B-tree.
     *
     * @param node  The current node to search in.
     * @param title The title of the document to search for.
     * @return The DocumentBTreeNode containing the document if found, otherwise null.
     */
    private Document searchRecursive(DocumentBTreeNode node, String title) {
        if (node == null) {
            return null;
        }

        int i = 0;
        while (i < node.n && title.compareTo(node.documents.get(i).title) > 0) {
            i++;
        }

        if (i < node.n && node.documents.get(i).title.equals(title)) {
            return node.documents.get(i);
        }

        if (node.leaf) {
            return null;
        } else {
            return searchRecursive(node.children.get(i), title);
        }
    }

//    public Document getDocument(String title) {
//        DocumentBTreeNode node = search(title);
//        if (node != null) {
//            for (Document doc : node.documents) {
//                if (doc.title.equals(title)) {
//                    return doc;
//                }
//            }
//        }
//        return null;
//    }

    /**
     * Searches for documents in the B-tree that match a given pattern.
     *
     * @param pattern The pattern to search for in the documents.
     * @return A list of documents that match the given pattern.
     */
    public List<Document> searchDocuments(String pattern) {
        List<Document> foundDocuments = new ArrayList<>();
        int[] piTable = KMPAlgorithm.generatePiTable(pattern);
        searchDocumentsRecursive(root, pattern, piTable, foundDocuments);
        return foundDocuments;
    }

    /**
     * Recursively searches for documents in the subtree rooted at the given node that match a given pattern.
     *
     * @param node           The current node in the B-tree.
     * @param pattern        The pattern to search for in the documents.
     * @param piTable        The pi table used for KMP algorithm.
     * @param foundDocuments The list of found documents matching the pattern.
     */
    private void searchDocumentsRecursive(DocumentBTreeNode node, String pattern, int[] piTable, List<Document> foundDocuments) {
        if (node == null) {
            return;
        }

        for (Document doc : node.documents) {
            if (KMPAlgorithm.KMP(doc.title, pattern, piTable) ||
                    KMPAlgorithm.KMP(doc.author, pattern, piTable) ||
                    KMPAlgorithm.KMP(doc.publishingYear + "", pattern, piTable) ||
                    KMPAlgorithm.KMP(doc.abstractText, pattern, piTable) ||
                    (doc.isPublic && doc.link != null && KMPAlgorithm.KMP(doc.link, pattern, piTable))) {
                foundDocuments.add(doc);
            }
        }

        if (!node.leaf) {
            for (DocumentBTreeNode childNode : node.children) {
                if (childNode != null) {
                    searchDocumentsRecursive(childNode, pattern, piTable, foundDocuments);
                }
            }
        }
    }

    /**
     * Counts the number of documents stored in the B-tree.
     *
     * @return The total number of documents in the B-tree.
     */
    public int countDocuments() {
        return countDocumentsRecursive(root);
    }

    /**
     * Recursively counts the number of documents in the subtree rooted at the given node.
     *
     * @param node The root node of the subtree.
     * @return The total number of documents in the subtree.
     */
    private int countDocumentsRecursive(DocumentBTreeNode node) {
        if (node == null) {
            return 0;
        }

        int count = 0;
        // Count the documents in the current node
        count += node.documents.size();

        // If it's not a leaf, count documents in child nodes
        if (!node.leaf) {
            for (int i = 0; i <= node.n; i++) {
                count += countDocumentsRecursive(node.children.get(i));
            }
        }

        return count;
    }

    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1); // Assuming '|' is the delimiter
                if (parts.length >= 6) {
                    String title = parts[0];
                    String author = parts[1];
                    int publishingYear = Integer.parseInt(parts[2]);
                    String abstractText = parts[3];
                    boolean isPublic = Boolean.parseBoolean(parts[4]);
                    String link = parts[5];
                    insert(new Document(title, author, publishingYear, abstractText, isPublic, link));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            saveNodeToFile(root, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveNodeToFile(DocumentBTreeNode node, BufferedWriter writer) throws IOException {
        if (node == null) {
            return;
        }

        // Write each document in the current node
        for (Document doc : node.documents) {
            writer.write(formatDocumentForFile(doc));
            writer.newLine();
        }

        // Recursively write children if it's not a leaf
        if (!node.leaf) {
            for (DocumentBTreeNode child : node.children) {
                saveNodeToFile(child, writer);
            }
        }
    }

    private String formatDocumentForFile(Document doc) {
        return doc.title + "|" + doc.author + "|" + doc.publishingYear + "|" +
                doc.abstractText + "|" + doc.isPublic + "|" + (doc.link != null ? doc.link : "");
    }

    /**
     * Deletes a document from the B-tree.
     *
     * @param doc The document to be deleted.
     */
    public void delete(Document doc) {
        if (root == null) {
            return;
        }
        deleteRecursive(root, doc);

        // If the root node has 0 keys, make its first child as the new root
        // if it has a child, otherwise set root as null.
        if (root.n == 0) {
            if (root.leaf) {
                root = null;
            } else {
                root = root.children.get(0);
            }
        }
    }

    private void deleteRecursive(DocumentBTreeNode node, Document doc) {
        int idx = findKeyIndex(node, doc);

        // Case 1: The key to be removed is present in this node
        if (idx < node.n && node.documents.get(idx).title.compareTo(doc.title) == 0) {
            if (node.leaf) {
                // The node is a leaf, so simply remove the key from the node
                removeFromLeaf(node, idx);
            } else {
                // The node is not a leaf, remove from non-leaf node
                removeFromNonLeaf(node, idx);
            }
        } else {
            // The key is not present in this node

            // If this node is a leaf node, then the key is not present in the tree
            if (node.leaf) {
                return;
            }

            // The key to be removed is present in the sub-tree rooted with this node
            // The flag indicates whether the key is present in the sub-tree rooted
            // with the last child of this node
            boolean flag = ((idx == node.n) ? true : false);

            // If the child where the key is supposed to exist has less than t keys,
            // we fill that child
            if (node.children.get(idx).n < t) {
                fill(node, idx);
            }

            // If the last child has been merged, it must have merged with the previous
            // child and so we recurse on the (idx-1)th child. Else, we recurse on the
            // (idx)th child which now has at least t keys
            if (flag && idx > node.n) {
                deleteRecursive(node.children.get(idx - 1), doc);
            } else {
                deleteRecursive(node.children.get(idx), doc);
            }
        }
    }

    private int findKeyIndex(DocumentBTreeNode node, Document doc) {
        int idx = 0;
        while (idx < node.n && node.documents.get(idx).title.compareTo(doc.title) < 0) {
            ++idx;
        }
        return idx;
    }

    private void removeFromLeaf(DocumentBTreeNode node, int idx) {
        // Shift all keys after idx to the left
        for (int i = idx + 1; i < node.n; ++i) {
            node.documents.set(i - 1, node.documents.get(i));
        }
        node.n--;
    }

    private void removeFromNonLeaf(DocumentBTreeNode node, int idx) {
        Document doc = node.documents.get(idx);

        // Check if the child that precedes doc has at least t keys
        if (node.children.get(idx).n >= t) {
            Document pred = getPred(node, idx);
            node.documents.set(idx, pred);
            deleteRecursive(node.children.get(idx), pred);
        }
        // Check if the child that follows doc has at least t keys
        else if (node.children.get(idx + 1).n >= t) {
            Document succ = getSucc(node, idx);
            node.documents.set(idx, succ);
            deleteRecursive(node.children.get(idx + 1), succ);
        }
        // Merge children.get(idx) with children[idx+1] and delete doc from children.get(idx)
        else {
            merge(node, idx);
            deleteRecursive(node.children.get(idx), doc);
        }
    }

    // Get predecessor of documents.get(idx)
    private Document getPred(DocumentBTreeNode node, int idx) {
        // Keep moving to the rightmost node until we reach a leaf
        DocumentBTreeNode cur = node.children.get(idx);
        while (!cur.leaf) {
            cur = cur.children.get(cur.n);
        }
        // Return the last document of the leaf
        return cur.documents.get(cur.n - 1);
    }

    // Get successor of documents.get(idx)
    private Document getSucc(DocumentBTreeNode node, int idx) {
        // Keep moving the leftmost node starting from children[idx+1] until we reach a leaf
        DocumentBTreeNode cur = node.children.get(idx + 1);
        while (!cur.leaf) {
            cur = cur.children.get(0);
        }
        // Return the first document of the leaf
        return cur.documents.get(0);
    }

    /**
     * Merges two nodes at a given index. It assumes that the nodes are not full.
     *
     * @param node The parent node of the two nodes to merge.
     * @param idx  The index of the left node in the parent node's children array.
     */
    private void merge(DocumentBTreeNode node, int idx) {
        DocumentBTreeNode child = node.children.get(idx);
        DocumentBTreeNode sibling = node.children.get(idx + 1);

        // Pulling a document from the current node and inserting it into (t-1)th position of children.get(idx)
        child.documents.set(t - 1, node.documents.get(idx));

        // Copying the documents from children[idx+1] to children.get(idx) at the end
        for (int i = 0; i < sibling.n; ++i) {
            child.documents.set(i + t, sibling.documents.get(i));
        }

        // Copying the child pointers from children[idx+1] to children.get(idx)
        if (!child.leaf) {
            for (int i = 0; i <= sibling.n; ++i) {
                child.children.set(i + t, sibling.children.get(i));
            }
        }

        // Moving all documents after idx in the current node one step before to fill the gap created by moving documents.get(idx) to children.get(idx)
        for (int i = idx + 1; i < node.n; ++i) {
            node.documents.set(i - 1, node.documents.get(i));
        }
        // Moving the child pointers after (idx+1) in the current node one step before
        for (int i = idx + 2; i <= node.n; ++i) {
            node.children.set(i - 1, node.children.get(i));
        }

        // Updating the document count of the children nodes
        child.n += sibling.n + 1;
        node.n--;
    }

    private void fill(DocumentBTreeNode node, int idx) {
        // If the previous child has more than t-1 keys, borrow a key
        // from that child
        if (idx != 0 && node.children.get(idx - 1).n >= t) {
            borrowFromPrev(node, idx);
        }

        // If the next child has more than t-1 keys, borrow a key
        // from that child
        else if (idx != node.n && node.children.get(idx + 1).n >= t) {
            borrowFromNext(node, idx);
        }

        // Merge the child with its sibling
        // If the child is the last child, merge it with its previous sibling
        // Otherwise merge it with its next sibling
        else {
            if (idx != node.n) {
                merge(node, idx);
            } else {
                merge(node, idx - 1);
            }
        }
    }

    private void borrowFromPrev(DocumentBTreeNode node, int idx) {
        DocumentBTreeNode child = node.children.get(idx);
        DocumentBTreeNode sibling = node.children.get(idx - 1);

        // Move all keys in child one step ahead
        for (int i = child.n - 1; i >= 0; i--) {
            child.documents.set(i + 1, child.documents.get(i));
        }

        // If child is not a leaf, move its child pointers one step ahead
        if (!child.leaf) {
            for (int i = child.n; i >= 0; i--) {
                child.children.set(i + 1, child.children.get(i));
            }
        }

        // Set the first key of the child to the keys[idx-1] from the current node
        child.documents.set(0, node.documents.get(idx - 1));

        // Moving sibling's last child as child's first child
        if (!node.leaf) {
            child.children.set(0, sibling.children.get(sibling.n));
        }

        // Moving the key from the sibling to the parent
        // This reduces the number of keys in the sibling
        node.documents.set(idx - 1, sibling.documents.get(sibling.n - 1));

        child.n += 1;
        sibling.n -= 1;
    }

    private void borrowFromNext(DocumentBTreeNode node, int idx) {
        DocumentBTreeNode child = node.children.get(idx);
        DocumentBTreeNode sibling = node.children.get(idx + 1);

        // keys.get(idx) is inserted as the last key in child
        child.documents.set(child.n, node.documents.get(idx));

        // Sibling's first child is inserted as the last child into child
        if (!child.leaf) {
            child.children.set(child.n + 1, sibling.children.get(0));
        }

        // The first key from sibling is inserted into keys.get(idx)
        node.documents.set(idx, sibling.documents.get(0));

        // Moving all keys in sibling one step behind
        for (int i = 1; i < sibling.n; i++) {
            sibling.documents.set(i - 1, sibling.documents.get(i));
        }

        // Moving the child pointers one step behind
        if (!sibling.leaf) {
            for (int i = 1; i <= sibling.n; i++) {
                sibling.children.set(i - 1, sibling.children.get(i));
            }
        }

        child.n += 1;
        sibling.n -= 1;
    }

}
//package org.example;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DocumentBTree {
//    private DocumentBTreeNode root;
//    private int t;
//
//    public DocumentBTree(int t) {
//        this.root = null;
//        this.t = t;
//    }
//
//    public void insert(Document doc) {
//        if (root == null) {
//            root = new DocumentBTreeNode(t, true);
//            root.documents.add(doc);
//            root.n = 1;
//        } else {
//            // If root is full, then the tree grows in height
//            if (root.n == 2 * t - 1) {
//                DocumentBTreeNode newRoot = new DocumentBTreeNode(t, false);
//                newRoot.children[0] = root;
//                split(newRoot, 0, root);
//                root = newRoot;
//            }
//            insertNonFull(root, doc);
//        }
//    }
//
//    private void insertNonFull(DocumentBTreeNode node, Document doc) {
//        int i = node.n - 1;
//
//        if (node.leaf) {
//            while (i >= 0 && node.documents.get(i).title.compareTo(doc.title) > 0) {
//                if (i < node.documents.size() - 1) {
//                    node.documents.set(i + 1, node.documents.get(i));
//                } else {
//                    node.documents.add(node.documents.get(i));
//                }
//                i--;
//            }
//            if (i == -1 || !node.documents.get(i).title.equals(doc.title)) {
//                if (i == -1) {
//                    node.documents.add(0, doc);
//                } else {
//                    node.documents.add(i + 1, doc);
//                }
//                node.n++;
//            }
//        } else {
//            while (i >= 0 && node.documents.get(i).title.compareTo(doc.title) > 0) {
//                i--;
//            }
//            i++;
//            if (node.children[i].n == 2 * t - 1) {
//                split(node, i, node.children[i]);
//                if (doc.title.compareTo(node.documents.get(i).title) > 0) {
//                    i++;
//                }
//            }
//            insertNonFull(node.children[i], doc);
//        }
//    }
//
//
//    private void split(DocumentBTreeNode parent, int i, DocumentBTreeNode child) {
//        DocumentBTreeNode newNode = new DocumentBTreeNode(t, child.leaf);
//        newNode.n = t - 1;
//
//        for (int j = 0; j < t - 1; j++) {
//            newNode.documents.add(child.documents.get(j + t));
//        }
//
//        if (!child.leaf) {
//            for (int j = 0; j < t; j++) {
//                newNode.children[j] = child.children[j + t];
//            }
//        }
//
//        child.n = t - 1;
//
//        for (int j = parent.n; j >= i + 1; j--) {
//            parent.children[j + 1] = parent.children[j];
//        }
//        parent.children[i + 1] = newNode;
//
//        for (int j = parent.n - 1; j >= i; j--) {
//            parent.documents.add(j + 1, parent.documents.get(j));
//        }
//
//        parent.documents.add(i, child.documents.get(t - 1));
//        parent.n++;
//    }
//
//
//    public DocumentBTreeNode search(String title) {
//        return searchRecursive(root, title);
//    }
//
//    private DocumentBTreeNode searchRecursive(DocumentBTreeNode node, String title) {
//        if (node == null) {
//            return null;
//        }
//
//        for (Document doc : node.documents) {
//            if (doc.title.equals(title)) {
//                return node;
//            }
//        }
//
//        if (title.compareTo(node.documents.get(node.n - 1).title) > 0) {
//            return searchRecursive(node.children[node.n], title);
//        }
//
//        for (int i = 0; i < node.n; i++) {
//            if (title.compareTo(node.documents.get(i).title) < 0) {
//                return searchRecursive(node.children[i], title);
//            }
//        }
//
//        return null;
//    }
//
//    public Document getDocument(String title) {
//        DocumentBTreeNode node = search(title);
//        if (node != null) {
//            for (Document doc : node.documents) {
//                if (doc.title.equals(title)) {
//                    return doc;
//                }
//            }
//        }
//        return null;
//    }
//
//    public List<Document> searchDocuments(String pattern) {
//        List<Document> foundDocuments = new ArrayList<>();
//        int[] piTable = KMPAlgorithm.generatePiTable(pattern);
//        searchDocumentsRecursive(root, pattern, piTable, foundDocuments);
//        return foundDocuments;
//    }
//
//    private void searchDocumentsRecursive(DocumentBTreeNode node, String pattern, int[] piTable, List<Document> foundDocuments) {
//        if (node == null) {
//            return;
//        }
//
//        for (Document doc : node.documents) {
//            if (KMPAlgorithm.KMP(doc.title, pattern, piTable) ||
//                    KMPAlgorithm.KMP(doc.author, pattern, piTable) ||
//                    KMPAlgorithm.KMP(doc.publishingYear + "", pattern, piTable) ||
//                    KMPAlgorithm.KMP(doc.abstractText, pattern, piTable) ||
//                    (doc.isPublic && doc.link != null && KMPAlgorithm.KMP(doc.link, pattern, piTable))) {
//                foundDocuments.add(doc);
//            }
//        }
//
//        if (!node.leaf) {
//            for (DocumentBTreeNode childNode : node.children) {
//                if (childNode != null) {
//                    searchDocumentsRecursive(childNode, pattern, piTable, foundDocuments);
//                }
//            }
//        }
//    }
//    public int countDocuments() {
//        return countDocumentsRecursive(root);
//    }
//
//    private int countDocumentsRecursive(DocumentBTreeNode node) {
//        if (node == null) {
//            return 0;
//        }
//
//        int count = 0;
//        // Count the documents in the current node
//        count += node.documents.size();
//
//        // If it's not a leaf, count documents in child nodes
//        if (!node.leaf) {
//            for (int i = 0; i <= node.n; i++) {
//                count += countDocumentsRecursive(node.children[i]);
//            }
//        }
//
//        return count;
//    }
//}