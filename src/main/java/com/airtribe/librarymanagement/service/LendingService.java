
package com.airtribe.librarymanagement.service;

import com.airtribe.librarymanagement.model.BookCopy;
import com.airtribe.librarymanagement.model.BookCopyStatus;
import com.airtribe.librarymanagement.model.Loan;
import com.airtribe.librarymanagement.model.Patron;
import com.airtribe.librarymanagement.observer.LibraryObserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.airtribe.librarymanagement.util.IdGenerator.generateLoanId;

public class LendingService {

    private final BookService bookService;
    private final PatronService patronService;

    private final Map<String, Loan> loans = new HashMap<>();
    private final List<LibraryObserver> observers = new ArrayList<>();

    private static final Logger logger =
            Logger.getLogger(LendingService.class.getName());

    public LendingService(
            BookService bookService,
            PatronService patronService
    ) {
        if (bookService == null || patronService == null) {
            throw new IllegalArgumentException(
                    "BookService and PatronService cannot be null."
            );
        }

        this.bookService = bookService;
        this.patronService = patronService;
    }

    public Loan borrowBook(String copyId, Patron patron) {
        BookCopy bookCopy = bookService.getBookCopyById(copyId);

        if (patron == null) {
            throw new IllegalArgumentException(
                    "Patron cannot be null."
            );
        }

        // Only registered patrons can borrow books.
        Patron registeredPatron =
                patronService.getPatronById(patron.getPatronId());

        if (bookCopy.getStatus() != BookCopyStatus.AVAILABLE) {
            throw new IllegalStateException(
                    "Book copy is not available."
            );
        }

        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(7);

        Loan loan = new Loan(
                generateLoanId(),
                bookCopy,
                registeredPatron,
                borrowDate,
                dueDate
        );

        bookCopy.markBorrowed();
        loans.put(loan.getLoanId(), loan);

        logger.info(
                "Book borrowed successfully. Loan ID: " + loan.getLoanId()
        );

        notifyBookBorrowed(loan);

        return loan;
    }

    public void returnBook(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }

        Loan storedLoan = loans.get(loan.getLoanId());

        if (storedLoan == null) {
            throw new IllegalArgumentException(
                    "Loan not found: " + loan.getLoanId()
            );
        }

        storedLoan.markReturned(LocalDate.now());
        storedLoan.getBookCopy().markAvailable();

        logger.info(
                "Book returned successfully. Loan ID: "
                        + storedLoan.getLoanId()
        );
    }

    public List<Loan> getBorrowingHistory(String patronId) {
        patronService.getPatronById(patronId);

        List<Loan> history = new ArrayList<>();

        for (Loan loan : loans.values()) {
            if (loan.getPatron().getPatronId().equals(patronId)) {
                history.add(loan);
            }
        }

        return history;
    }

    public List<Loan> getAllLoans() {
        return new ArrayList<>(loans.values());
    }

    public void addObserver(LibraryObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException(
                    "Observer cannot be null."
            );
        }

        observers.add(observer);
    }

    private void notifyBookBorrowed(Loan loan) {
        for (LibraryObserver observer : observers) {
            observer.onBookBorrowed(loan);
        }
    }
}
