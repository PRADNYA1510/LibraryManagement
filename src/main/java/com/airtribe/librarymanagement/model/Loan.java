package com.airtribe.librarymanagement.model;

import java.time.LocalDate;

public class Loan {
    private final String loanId;
    private final BookCopy bookCopy;
    private final Patron patron;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(String loanId, BookCopy bookCopy, Patron patron, LocalDate borrowDate, LocalDate dueDate) {
        validateText(loanId,"LoanID");
        validateDate(borrowDate,"BorrowDate");
        validateDueDate(dueDate,borrowDate);
        if (bookCopy == null || patron == null) {
            throw new IllegalArgumentException(
                    "Book and patron cannot be null."
            );
        }
        this.loanId = loanId;
        this.bookCopy = bookCopy;
        this.patron = patron;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
    }
    private void validateText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " cannot be null or blank."
            );
        }
    }
    private void validateDate(LocalDate value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(
                    fieldName + " cannot be null or blank."
            );
        }
    }
    private void validateDueDate(LocalDate value,LocalDate borrowDate) {
        if (value == null) {
            throw new IllegalArgumentException("Due date cannot be null.");
        }

        if (value.isBefore(borrowDate)) {
            throw new IllegalArgumentException(
                    "Due date cannot be before borrow date."
            );
        }
    }

    public String getLoanId() {
        return loanId;
    }

//    public void setLoanId(String loanId) {
//        this.loanId = loanId;
//    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

//    public void setBook(Book book) {
//        this.book = book;
//    }

    public Patron getPatron() {
        return patron;
    }

//    public void setPatron(Patron patron) {
//        this.patron = patron;
//    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

//    public void setBorrowDate(LocalDate borrowDate) {
//        this.borrowDate = borrowDate;
//    }

    public LocalDate getDueDate() {
        return dueDate;
    }

//    public void setDueDate(LocalDate dueDate) {
//        this.dueDate = dueDate;
//    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
//
//    public void setReturnDate(LocalDate returnDate) {
//        this.returnDate = returnDate;
//    }
public void markReturned(LocalDate returnDate){
    if (returnDate == null) {
        throw new IllegalArgumentException(
                "Return date cannot be null."
        );
    }

    if (returnDate.isBefore(this.borrowDate)) {
        throw new IllegalArgumentException(
                "Return date cannot be before borrow date."
        );
    }

    if (this.returnDate != null) {
        throw new IllegalStateException(
                "This loan has already been returned."
        );
    }

    this.returnDate = returnDate;
}
}
