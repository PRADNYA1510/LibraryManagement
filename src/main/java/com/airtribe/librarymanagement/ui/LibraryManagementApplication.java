package com.airtribe.librarymanagement.ui;

import com.airtribe.librarymanagement.model.Book;
import com.airtribe.librarymanagement.model.BookCopy;
import com.airtribe.librarymanagement.model.Patron;
import com.airtribe.librarymanagement.observer.ConsoleObserver;
import com.airtribe.librarymanagement.observer.EmailObserver;
import com.airtribe.librarymanagement.service.LibraryService;
import com.airtribe.librarymanagement.factory.BookFactory;

import java.util.Scanner;

public class LibraryManagementApplication {


    public static void main(String[] args) {

        LibraryService libraryService = new LibraryService();

        // Register observers
        libraryService.addObserver(new ConsoleObserver());
        libraryService.addObserver(new EmailObserver());

        Scanner scanner = new Scanner(System.in);
        BookFactory bookFactory = new BookFactory();

        Book book = bookFactory.createBook(
                "Clean Code",
                "Robert C. Martin",
                "ISBN-101",
                2008
        );

        BookCopy bookCopy = new BookCopy("COPY-101", book);
        Patron patron = new Patron(
                "P-101",
                "Pradnya",
                "pradnya@example.com"
        );

        libraryService.addBookCopy(bookCopy);
        libraryService.registerPatron(patron);

        boolean running = true;

        while (running) {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. View all book copies");
            System.out.println("2. Search books by title");
            System.out.println("3. Register a patron");
            System.out.println("4. Borrow a book");
            System.out.println("5. Return a book");
            System.out.println("6. View patron borrowing history");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        libraryService.getAllBookCopies()
                                .forEach(System.out::println);
                        break;

                    case "2":
                        System.out.print("Enter book title: ");
                        String title = scanner.nextLine();

                        libraryService.searchBookCopiesByTitle(title)
                                .forEach(System.out::println);
                        break;

                    case "3":
                        System.out.print("Enter patron ID: ");
                        String patronId = scanner.nextLine();

                        System.out.print("Enter patron name: ");
                        String name = scanner.nextLine();

                        System.out.print("Enter patron email: ");
                        String email = scanner.nextLine();

                        libraryService.registerPatron(
                                new Patron(patronId, name, email)
                        );

                        System.out.println("Patron registered successfully.");
                        break;

                    case "4":
                        System.out.print("Enter copy ID: ");
                        String copyId = scanner.nextLine();

                        System.out.print("Enter patron ID: ");
                        String borrowerId = scanner.nextLine();

                        Patron borrower =
                                libraryService.getPatronById(borrowerId);

                        if (borrower == null) {
                            System.out.println("Patron not found.");
                            break;
                        }

                        libraryService.borrowBook(copyId, borrower);
                        System.out.println("Book borrowed successfully.");
                        break;

                    case "5":
                        System.out.print("Enter loan ID: ");
                        String loanId = scanner.nextLine();

                        var loan = libraryService.getAllLoans()
                                .stream()
                                .filter(item ->
                                        item.getLoanId().equals(loanId))
                                .findFirst()
                                .orElse(null);

                        if (loan == null) {
                            System.out.println("Loan not found.");
                            break;
                        }

                        libraryService.returnBook(loan);
                        System.out.println("Book returned successfully.");
                        break;

                    case "6":
                        System.out.print("Enter patron ID: ");
                        String historyPatronId = scanner.nextLine();

                        libraryService.getBorrowingHistory(historyPatronId)
                                .forEach(System.out::println);
                        break;

                    case "7":
                        running = false;
                        System.out.println("Exiting Library Management System.");
                        break;

                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
