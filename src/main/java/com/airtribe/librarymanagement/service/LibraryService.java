
package com.airtribe.librarymanagement.service;

import com.airtribe.librarymanagement.model.BookCopy;
import com.airtribe.librarymanagement.model.Loan;
import com.airtribe.librarymanagement.model.Patron;
import com.airtribe.librarymanagement.observer.LibraryObserver;

import java.util.List;

public class LibraryService {

    private final BookService bookService;
    private final PatronService patronService;
    private final LendingService lendingService;

    public LibraryService() {
        this.bookService = new BookService();
        this.patronService = new PatronService();
        this.lendingService = new LendingService(
                bookService,
                patronService
        );
    }

    // Book operations

    public void addBookCopy(BookCopy bookCopy) {
        bookService.addBookCopy(bookCopy);
    }

    public BookCopy getBookCopyById(String copyId) {
        return bookService.getBookCopyById(copyId);
    }

    public void removeBookCopy(BookCopy bookCopy) {
        if (bookCopy == null) {
            throw new IllegalArgumentException(
                    "Book copy cannot be null."
            );
        }

        bookService.removeBookCopy(bookCopy.getCopyId());
    }

    public List<BookCopy> searchBookCopiesByTitle(String title) {
        return bookService.searchBookCopiesByTitle(title);
    }

    public List<BookCopy> searchBookCopiesByAuthor(String author) {
        return bookService.searchBookCopiesByAuthor(author);
    }

    public List<BookCopy> searchBookCopiesByIsbn(String isbn) {
        return bookService.searchBookCopiesByIsbn(isbn);
    }

    public void updateBookDetails(
            String isbn,
            String title,
            String author,
            int publicationYear
    ) {
        bookService.updateBookDetails(
                isbn, title, author, publicationYear
        );
    }

    public List<BookCopy> getAllBookCopies() {
        return bookService.getAllBookCopies();
    }

    // Patron operations

    public void registerPatron(Patron patron) {
        patronService.registerPatron(patron);
    }

    public Patron getPatronById(String patronId) {
        return patronService.getPatronById(patronId);
    }

    public void updatePatronInfo(
            String patronId,
            String name,
            String email
    ) {
        patronService.updatePatronInfo(patronId, name, email);
    }

    public void removePatron(String patronId) {
        patronService.removePatron(
                patronId,
                lendingService.getAllLoans()
        );
    }

    public List<Patron> getAllPatrons() {
        return patronService.getAllPatrons();
    }

    // Lending operations

    public Loan borrowBook(String copyId, Patron patron) {
        return lendingService.borrowBook(copyId, patron);
    }

    public void returnBook(Loan loan) {
        lendingService.returnBook(loan);
    }

    public List<Loan> getBorrowingHistory(String patronId) {
        return lendingService.getBorrowingHistory(patronId);
    }

    public List<Loan> getAllLoans() {
        return lendingService.getAllLoans();
    }

    // Observer registration

    public void addObserver(LibraryObserver observer) {
        lendingService.addObserver(observer);
    }
}
