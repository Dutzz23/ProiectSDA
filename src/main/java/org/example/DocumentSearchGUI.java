package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;

/**
 * GUI for searching and managing documents using B-tree and Red-Black tree structures.
 */
public class DocumentSearchGUI extends Frame {
    private TextField inputField;
    private java.awt.List resultsList = new java.awt.List(0);
    private DocumentBTree documentBTree;
    private DocumentRBTree documentRBTree;

    private String searchedValue = "";

    private Button searchButton;
    private Button historyButton;

    public DocumentSearchGUI() {
        // Initialize the B-Trees
        documentBTree = new DocumentBTree(4);
        documentRBTree = new DocumentRBTree();

        // Populate the DocumentBTree with some documents
        documentBTree.insert(new Document("Efficient Algorithms in Quantum Computing", "Alice Johnson", 2021, "Exploring the efficiency of new algorithms in quantum computing environments.", true, "http://research-link1.com"));
        documentBTree.insert(new Document("Advancements in Artificial Neural Networks", "Bob Smith", 2020, "A comprehensive study on the latest advancements in artificial neural networks.", true, "http://research-link2.com"));
        documentBTree.insert(new Document("Cybersecurity Trends in 2021", "Carol White", 2021, "An analysis of emerging trends and threats in cybersecurity.", true, "http://research-link3.com"));
        documentBTree.insert(new Document("Machine Learning and Healthcare", "David Brown", 2019, "Investigating the impact of machine learning technologies in healthcare.", true, "http://research-link4.com"));
        documentBTree.insert(new Document("Blockchain Technology Explained", "Eve Davis", 2018, "Demystifying blockchain technology and its practical applications.", false, ""));
        documentBTree.insert(new Document("The Future of Virtual Reality", "Frank Green", 2021, "Predicting the trajectory and potential future applications of virtual reality.", true, "http://research-link5.com"));
        documentBTree.insert(new Document("Understanding Distributed Systems", "Grace Hall", 2019, "A detailed overview of distributed systems and their applications.", true, "http://research-link6.com"));
        documentBTree.insert(new Document("Exploring Cryptography", "Henry Irvine", 2020, "New advancements in cryptographic techniques and their security implications.", false, ""));
        documentBTree.insert(new Document("Data Mining Techniques", "Isabel Yamamoto", 2018, "An exploration of various data mining techniques in large datasets.", true, "http://research-link7.com"));
        documentBTree.insert(new Document("Cloud Computing and Storage", "Jack Knox", 2021, "The evolution of cloud computing and its impact on data storage solutions.", true, "http://research-link8.com"));
        documentBTree.insert(new Document("Big Data Analytics", "Karen Quinn", 2020, "Understanding the power and challenges of big data analytics in modern business.", false, ""));
        documentBTree.insert(new Document("Internet of Things (IoT) Security", "Leo Ortiz", 2019, "Addressing security concerns in the rapidly growing field of IoT.", true, "http://research-link9.com"));
        documentBTree.insert(new Document("Advances in Robotics", "Mia Turner", 2021, "The latest technological advances in the field of robotics.", true, "http://research-link10.com"));
        documentBTree.insert(new Document("Natural Language Processing", "Nolan Beck", 2018, "Exploring the complexities of language processing by machines.", false, ""));
        documentBTree.insert(new Document("Computer Vision in Autonomous Vehicles", "Olivia Kane", 2020, "Analyzing the role of computer vision in the development of autonomous vehicles.", true, "http://research-link11.com"));
        documentBTree.insert(new Document("Ethical AI", "Peter Lowell", 2019, "Discussion on the ethical implications of artificial intelligence.", true, "http://research-link12.com"));
        documentBTree.insert(new Document("Quantum Computing Applications", "Quinn Ebert", 2021, "Exploring practical applications of quantum computing.", true, "http://research-link13.com"));
        documentBTree.insert(new Document("Augmented Reality in Education", "Rachel Ivanov", 2020, "How augmented reality is changing the educational landscape.", false, ""));
        documentBTree.insert(new Document("5G Technology and Its Impact", "Sam Yu", 2019, "An in-depth look at 5G technology and its potential impact on communication.", true, "http://research-link14.com"));
        documentBTree.insert(new Document("Information Retrieval Systems", "Tina Rowe", 2018, "Exploring the efficiency and accuracy of various information retrieval systems.", true, "http://research-link15.com"));
        documentBTree.insert(new Document("The Evolution of Social Media Algorithms", "Uma Patel", 2021, "A study on how social media algorithms have evolved over the years.", false, ""));
        documentBTree.insert(new Document("Blockchain in Financial Services", "Victor Dale", 2020, "The role of blockchain technology in transforming financial services.", true, "http://research-link16.com"));
        documentBTree.insert(new Document("Wireless Sensor Networks", "Wendy Harris", 2018, "An overview of the development and application of wireless sensor networks.", true, "http://research-link17.com"));
        documentBTree.insert(new Document("Cyber-Physical Systems", "Xavier Bloom", 2019, "Integrating computation with physical processes in cyber-physical systems.", false, ""));
        documentBTree.insert(new Document("Deep Learning in Medical Diagnosis", "Yasmine Ford", 2021, "Utilizing deep learning techniques for improving medical diagnosis accuracy.", true, "http://research-link18.com"));
        documentBTree.insert(new Document("Virtual Reality in Training", "Zane O'Donnell", 2020, "The application of virtual reality technology in professional training programs.", true, "http://research-link19.com"));

        // Set up the GUI
        setLayout(new BorderLayout());

        inputField = new TextField();
        inputField.addTextListener(new TextListener() {
            public void textValueChanged(TextEvent e) {
                updateSearchResults(inputField.getText());
            }
        });

        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                saveDocuments();
            }
        });

        resultsList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedItem = resultsList.getSelectedItem();
                // Assuming the format is "Index Title"
                String title = selectedItem.substring(selectedItem.indexOf(' ') + 1);
                Document selectedDoc = documentBTree.search(title);
                if (selectedDoc != null) {
                    showDocumentDetails(selectedDoc);
                }
            }
        });

        resultsList = new java.awt.List();

        add(inputField, BorderLayout.NORTH);
        add(resultsList, BorderLayout.CENTER);

        // Setup search button
        searchButton = new Button("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateSearchResults(inputField.getText());
                saveDocuments(); // Save the search results to RBTree
            }
        });

        // Setup history button
        historyButton = new Button("History");
        historyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayHistory(); // Implement this method to show search history
            }
        });

        // Add buttons to the GUI
        Panel topPanel = new Panel(new FlowLayout());
        topPanel.add(inputField);
        topPanel.add(searchButton);
        topPanel.add(historyButton); // Add the history button to the top panel
        add(topPanel, BorderLayout.NORTH);

        // Setup results list selection listener
        resultsList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateSearchResults(resultsList.getSelectedItem());
            }
        });

        setSize(400, 300);
        setVisible(true);
    }

    private void updateSearchResults(String searchText) {
        resultsList.removeAll();
        if (searchText.isEmpty()) {
            return; // Exit early if the search text is empty
        }
        this.searchedValue = searchText;
        java.util.List<Document> foundDocuments = documentBTree.searchDocuments(searchText);
        int i = 1;
        for (Document doc : foundDocuments) {
            resultsList.add(i++ + " " + doc.title); // Displaying the title of each found document
        }
        System.out.println("Searched: " + foundDocuments.size() + " " + documentBTree.countDocuments() + " " + searchText);
    }

    private void saveDocuments() {
        List<Document> currentDocuments = documentBTree.searchDocuments(inputField.getText());
        Date currentDate = new Date(); // Current date as the key
        if (currentDocuments.size() > 0) {
            documentRBTree.insert(currentDate, currentDocuments, this.searchedValue);
        }
    }

    private void displayHistory() {
        Frame historyFrame = new Frame("Search History");
        historyFrame.setLayout(new BorderLayout());

        java.awt.List historyList = new java.awt.List();
        displayHistoryRecursive(documentRBTree.getRoot(), historyList);

        historyFrame.add(historyList, BorderLayout.CENTER);
        historyFrame.setSize(400, 300);
        historyFrame.setVisible(true);
    }

    private void displayHistoryRecursive(DocumentRBTreeNode node, java.awt.List historyList) {
        if (node == null) {
            return;
        }
        // Traverse right subtree first (later dates)
        displayHistoryRecursive(node.right, historyList);

        // Process current node
        String displayText = node.key.toString() + " - " + node.searchedValue;
        historyList.add(displayText);

        // Traverse left subtree next (earlier dates)
        displayHistoryRecursive(node.left, historyList);
    }

    private void showDocumentDetails(Document doc) {
        Frame detailsFrame = new Frame("Document Details");
        detailsFrame.setLayout(new GridLayout(0, 2)); // Using GridLayout for simplicity

        // Title
        detailsFrame.add(new Label("Title:"));
        detailsFrame.add(new Label(doc.title));

        // Author
        detailsFrame.add(new Label("Author:"));
        detailsFrame.add(new Label(doc.author));

        // Publishing Year
        detailsFrame.add(new Label("Publishing Year:"));
        detailsFrame.add(new Label(Integer.toString(doc.publishingYear)));

        // Abstract Text
        detailsFrame.add(new Label("Abstract:"));
        detailsFrame.add(new Label(doc.abstractText));

        // Public/Private
        detailsFrame.add(new Label("Is Public:"));
        detailsFrame.add(new Label(doc.isPublic ? "Yes" : "No"));

        // Link (if available)
        detailsFrame.add(new Label("Link:"));
        detailsFrame.add(new Label(doc.isPublic && doc.link != null ? doc.link : "N/A"));

        // Delete Button
        Button deleteButton = new Button("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                documentBTree.delete(doc);
                detailsFrame.dispose(); // Close the details window
                updateSearchResults(inputField.getText()); // Refresh the search results
            }
        });

        detailsFrame.add(deleteButton);

        detailsFrame.pack();
        detailsFrame.setVisible(true);
    }
}
