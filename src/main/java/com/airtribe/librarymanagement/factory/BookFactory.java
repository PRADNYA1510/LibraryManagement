package com.airtribe.librarymanagement.factory;

import com.airtribe.librarymanagement.model.Book;

public class BookFactory {
    public Book createBook(
            String title,
            String author,
            String isbn,
            int publicationYear) {

        return new Book(title, author, isbn, publicationYear);
    }
}
