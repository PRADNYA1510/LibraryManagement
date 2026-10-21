
package com.airtribe.librarymanagement.service;

import com.airtribe.librarymanagement.model.Book;
import com.airtribe.librarymanagement.model.BookCopy;
import com.airtribe.librarymanagement.model.BookCopyStatus;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookService {

    private final Map<String, BookCopy> bookCopies = new HashMap<>();

    public void addBookCopy(BookCopy bookCopy) {
        if (bookCopy == null) {
            throw new IllegalArgumentException("Book copy cannot be null.");
        }

        String copyId = bookCopy.getCopyId();

        if (bookCopies.containsKey(copyId)) {
            throw new IllegalArgumentException(
                    "A book copy with this ID already exists: " + copyId
            );
        }

        bookCopies.put(copyId, bookCopy);
    }

    public BookCopy getBookCopyById(String copyId) {
        if (copyId == null || copyId.isBlank()) {
            throw new IllegalArgumentException(
                    "Book copy ID cannot be null or blank."
            );
        }

        BookCopy copy = bookCopies.get(copyId);

        if (copy == null) {
            throw new IllegalArgumentException(
                    "Book copy not found: " + copyId
            );
        }

        return copy;
    }

    public void removeBookCopy(String copyId) {
        BookCopy copy = getBookCopyById(copyId);

        if (copy.getStatus() != BookCopyStatus.AVAILABLE) {
            throw new IllegalStateException(
                    "Cannot remove a borrowed book copy."
            );
        }

        bookCopies.remove(copyId);
    }

    public List<BookCopy> searchBookCopiesByTitle(String title) {
        validateSearchValue(title, "Book title");

        List<BookCopy> results = new ArrayList<>();

        for (BookCopy copy : bookCopies.values()) {
            if (copy.getBook().getTitle().equalsIgnoreCase(title)) {
                results.add(copy);
            }
        }

        return results;
    }

    public List<BookCopy> searchBookCopiesByAuthor(String author) {
        validateSearchValue(author, "Author");

        List<BookCopy> results = new ArrayList<>();

        for (BookCopy copy : bookCopies.values()) {
            if (copy.getBook().getAuthor().equalsIgnoreCase(author)) {
                results.add(copy);
            }
        }

        return results;
    }

    public List<BookCopy> searchBookCopiesByIsbn(String isbn) {
        validateSearchValue(isbn, "ISBN");

        List<BookCopy> results = new ArrayList<>();

        for (BookCopy copy : bookCopies.values()) {
            if (copy.getBook().getIsbn().equalsIgnoreCase(isbn)) {
                results.add(copy);
            }
        }

        return results;
    }

    public void updateBookDetails(
            String isbn,
            String title,
            String author,
            int publicationYear
    ) {
        validateSearchValue(isbn, "ISBN");
        validateSearchValue(title, "Book title");
        validateSearchValue(author, "Author");

        int currentYear = Year.now().getValue();

        if (publicationYear < 1 || publicationYear > currentYear) {
            throw new IllegalArgumentException(
                    "Publication year must be between 1 and " + currentYear
            );
        }

        List<BookCopy> matchingCopies = searchBookCopiesByIsbn(isbn);

        if (matchingCopies.isEmpty()) {
            throw new IllegalArgumentException(
                    "ISBN not found: " + isbn
            );
        }

        // Copies of the same edition share the same Book object.
        Book book = matchingCopies.get(0).getBook();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublicationYear(publicationYear);
    }

    public List<BookCopy> getAllBookCopies() {
        return new ArrayList<>(bookCopies.values());
    }

    private void validateSearchValue(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " cannot be null or blank."
            );
        }
    }
}
