package com.airtribe.librarymanagement.observer;

import com.airtribe.librarymanagement.model.Loan;

public class ConsoleObserver implements LibraryObserver{
    @Override
    public void onBookBorrowed(Loan loan) {
        System.out.println(
                "Book borrowed: "
                        + loan.getBookCopy().getBook().getTitle()
                        + " by "
                        + loan.getPatron().getName()
        );
    }
}
