  package com.airtribe.librarymanagement.service;

import com.airtribe.librarymanagement.model.Book;
import com.airtribe.librarymanagement.model.BookCopy;
import com.airtribe.librarymanagement.model.BookCopyStatus;
import com.airtribe.librarymanagement.model.Loan;
import com.airtribe.librarymanagement.model.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryServiceTest {

    private LibraryService libraryService;
    private BookCopy bookCopy;
    private Patron patron;

    @BeforeEach
    void setUp() {
        libraryService = new LibraryService();

        Book book = new Book(
                "Clean Code",
                "Robert C. Martin",
                "ISBN-101",
                2008
        );

        bookCopy = new BookCopy("COPY-101", book);
        patron = new Patron(
                "P-101",
                "Pradnya",
                "pradnya@example.com"
        );

        libraryService.addBookCopy(bookCopy);
        libraryService.registerPatron(patron);
    }

    @Test
    void borrowBook_shouldMarkCopyAsBorrowed() {
        Loan loan = libraryService.borrowBook(
                bookCopy.getCopyId(),
                patron
        );

        assertNotNull(loan);
        assertEquals(
                BookCopyStatus.BORROWED,
                bookCopy.getStatus()
        );
    }

    @Test
    void borrowBook_shouldThrowWhenCopyIsAlreadyBorrowed() {
        libraryService.borrowBook(bookCopy.getCopyId(), patron);

        assertThrows(
                IllegalStateException.class,
                () -> libraryService.borrowBook(
                        bookCopy.getCopyId(),
                        patron
                )
        );
    }

    @Test
    void returnBook_shouldMarkCopyAsAvailable() {
        Loan loan = libraryService.borrowBook(
                bookCopy.getCopyId(),
                patron
        );

        libraryService.returnBook(loan);

        assertEquals(
                BookCopyStatus.AVAILABLE,
                bookCopy.getStatus()
        );
    }

    @Test
    void removePatron_shouldThrowWhenPatronHasActiveLoan() {
        libraryService.borrowBook(bookCopy.getCopyId(), patron);

        assertThrows(
                IllegalStateException.class,
                () -> libraryService.removePatron("P-101")
        );
    }
}
