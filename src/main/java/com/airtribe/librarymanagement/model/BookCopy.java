package com.airtribe.librarymanagement.model;

public class BookCopy {
//    copyId	String	Uniquely identifies a physical copy
//    book	Book	References the edition this copy belongs to
//    status	BookCopyStatus
    private String copyId;
    private Book book;
    private BookCopyStatus status;

    public BookCopy(String copyId, Book book) {
        validateText(copyId,"CopyID");
        this.copyId = copyId;
        this.book = book;
        this.status = BookCopyStatus.AVAILABLE;
    }

    private void validateText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " cannot be null or blank."
            );
        }
    }

    public String getCopyId() {
        return copyId;
    }

    public Book getBook() {
        return book;
    }

    public BookCopyStatus getStatus() {
        return status;
    }

    public void markBorrowed(){
        if(this.status==BookCopyStatus.BORROWED){
            throw new IllegalArgumentException(
                    "Book is Already borrowed."
            );
        }
        this.status = BookCopyStatus.BORROWED;
    }
    @Override
    public String toString() {
        return "BookCopy{" +
                "copyId='" + copyId + '\'' +
                ", book=" + book +
                ", status=" + status +
                '}';
    }

     public void markAvailable(){
         if(this.status==BookCopyStatus.AVAILABLE){
             throw new IllegalArgumentException(
                     "Book is Already available."
             );
         }
        this.status=BookCopyStatus.AVAILABLE;
     }
}
