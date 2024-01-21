package org.example;
/**
 * Represents a document with various attributes such as title, author, etc.
 */
public class Document {
    public final String title;
    public final String author;
    public final int publishingYear;
    public final String abstractText;
    public final boolean isPublic;
    public final String link; // Can be null

    /**
     * Constructor for creating a new document with a link.
     * @param title The title of the document.
     * @param author The author of the document.
     * @param publishingYear The year the document was published.
     * @param abstractText The abstract text of the document.
     * @param isPublic Flag indicating if the document is public.
     * @param link The link to the document (if public).
     */
    public Document(String title, String author, int publishingYear, String abstractText, boolean isPublic, String link) {
        this.title = title;
        this.author = author;
        this.publishingYear = publishingYear;
        this.abstractText = abstractText;
        this.isPublic = isPublic;
        if (isPublic)
            this.link = link;
        else
            this.link = null;
    }

    /**
     * Constructor for creating a new document without a link.
     * @param title The title of the document.
     * @param author The author of the document.
     * @param publishingYear The year the document was published.
     * @param abstractText The abstract text of the document.
     * @param isPublic Flag indicating if the document is public.
     */
    public Document(String title, String author, int publishingYear, String abstractText, boolean isPublic) {
        this.title = title;
        this.author = author;
        this.publishingYear = publishingYear;
        this.abstractText = abstractText;
        this.isPublic = isPublic;
        this.link = null;
    }
}
