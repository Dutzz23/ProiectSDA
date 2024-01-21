package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        new DocumentSearchGUI();
    }
//    public static void main(String[] args) {
//        DocumentBTree tree = new DocumentBTree(2);
//
//        // Insert documents
//        tree.insert(new Document("Title1", "Author1", 2020, "Abstract1", true, "http://link1.com"));
//        tree.insert(new Document("Title2", "Author2", 2019, "Abstract2", false, "http://link2.com"));
//        tree.insert(new Document("Title3", "Author3", 2021, "Abstract3", true, null));
//
//        // Search and print details of a document
//        String searchTitle = "Title2";
////        Document foundDoc = tree.getDocument(searchTitle);
////        if (foundDoc != null) {
////            printDocumentDetails(foundDoc);
////        } else {
////            System.out.println("Document not found: " + searchTitle);
////        }
//
//        String pattern = "19";  // The pattern to search for
//        List<Document> foundDocuments = tree.searchDocuments(pattern);
//
//        // Print the found documents
//        System.out.println("Documents containing pattern '" + pattern + "':");
//        for (Document doc : foundDocuments) {
//            System.out.println("Title: " + doc.title);
//            System.out.println("Author: " + doc.author);
//            System.out.println("Abstract: " + doc.abstractText);
//            System.out.println("Year: " + doc.publishingYear);
//            System.out.println("link: " + doc.link);
//            // You can print other details of the document as needed
//        }
//    }

    private static void printDocumentDetails(Document doc) {
        System.out.println("Title: " + doc.title);
        System.out.println("Author: " + doc.author);
        System.out.println("Publishing Year: " + doc.publishingYear);
        System.out.println("Is Public: " + doc.isPublic);
        System.out.println("Link: " + (doc.link != null ? doc.link : "None"));
        System.out.println("Abstract: " + doc.abstractText);
    }
}


//public class Main {
//    public static void main(String[] args) {
//        BTree tree = new BTree(2);
//        tree.insert(10);
//        tree.insert(20);
//        tree.insert(5);
//        tree.insert(6);
//        tree.insert(12);
//        tree.insert(30);
//        tree.insert(7);
//        tree.insert(17);
//        tree.insert(17);
//        tree.insert(186);
//        tree.insert(37);
//        tree.insert(15);
//        tree.insert(987);
//        tree.insert(64);
//        tree.insert(1);
//        tree.insert(27);
//        tree.insert(167);
//        tree.insert(94);
//        tree.insert(22);
//        tree.insert(68);
//        tree.insert(29);
//        tree.insert(54);
//
//        System.out.println("Traversal of the constructed tree:");
//        tree.traverse();
//
//        int key = 6;
//        System.out.println("\n\nSearching for key " + key);
//        if (tree.search(key) != null)
//            System.out.println("Key found");
//        else
//            System.out.println("Key not found");
//        tree.print();
//
//        RBTree Rtree = new RBTree();
//
//        int[] values = {15, 6, 18, 3, 7, 17, 20, 2};
//        for (int value : values) {
//            Rtree.insert(value);
//        }
//
//        System.out.println("Red-Black Tree after inserting values:");
//        Rtree.print();
//    }
//}