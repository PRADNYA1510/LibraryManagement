package com.airtribe.librarymanagement.observer;

import com.airtribe.librarymanagement.model.Loan;

public class EmailObserver implements LibraryObserver {

    @Override
    public void onBookBorrowed(Loan loan) {
        System.out.println(
                "Email notification: Book "
                        + loan.getBookCopy().getBook().getTitle()
                        + " borrowed by "
                        + loan.getPatron().getName()
        );
    }
}
