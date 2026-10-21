
package com.airtribe.librarymanagement.model;

import java.time.Year;

public class Book {

    private String title;
    private String author;
    private String isbn;
    private int publicationYear;

    public Book(String title, String author, String isbn, int publicationYear) {
        validateText(title, "Title");
        validateText(author, "Author");
        validateText(isbn, "ISBN");
        validatePublicationYear(publicationYear);

        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    private void validateText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " cannot be null or blank."
            );
        }
    }
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                '}';
    }

    private void validatePublicationYear(int publicationYear) {
        int currentYear = Year.now().getValue();

        if (publicationYear <= 0 || publicationYear > currentYear) {
            throw new IllegalArgumentException(
                    "Publication year must be between 1 and " + currentYear
            );
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        validateText(title, "Title");
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        validateText(author, "Author");
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        validateText(isbn, "ISBN");
        this.isbn = isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        validatePublicationYear(publicationYear);
        this.publicationYear = publicationYear;
    }
}
