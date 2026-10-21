## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   ├── com.airtribe.librarymanagement/
│   │   │   ├── factory/       # Book creation using Factory pattern
│   │   │   ├── model/         # Book, BookCopy, Patron, Loan
│   │   │   ├── observer/      # Observer pattern and notifications
│   │   │   ├── service/       # Library business logic
│   │   │   ├── ui/            # Console-based user interface
│   │   │   └── util/          # Utility classes and ID generation
│   │   └── org.example/
│   │       └── Main.java
│   └── resources/
└── test/
    └── java/
        └── com.airtribe.librarymanagement/
            └── service/
                └── LibraryServiceTest.java
```

## Architecture

The application uses a service layer to manage library operations.

- **Factory:** Creates `Book` objects.
- **Model:** Represents books, book copies, patrons, and loans.
- **Observer:** Notifies registered observers when a book is borrowed.
- **Service:** Separates book management, patron management, and lending operations.
- **UI:** Provides the console menu for interacting with the application.
- **Util:** Contains supporting utilities, such as ID generation.
- **Testing:** Uses JUnit to verify core library operations.

The application currently uses in-memory collections for data storage. A repository-based data access layer has not been integrated.
