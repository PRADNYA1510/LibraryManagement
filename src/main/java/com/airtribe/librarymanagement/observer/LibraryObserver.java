package com.airtribe.librarymanagement.observer;

import com.airtribe.librarymanagement.model.Loan;

public interface LibraryObserver {
    void onBookBorrowed(Loan loan);
}
