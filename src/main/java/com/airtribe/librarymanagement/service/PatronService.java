
package com.airtribe.librarymanagement.service;

import com.airtribe.librarymanagement.model.Loan;
import com.airtribe.librarymanagement.model.Patron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatronService {

    private final Map<String, Patron> patrons = new HashMap<>();

    public void registerPatron(Patron patron) {
        if (patron == null) {
            throw new IllegalArgumentException("Patron cannot be null.");
        }

        String patronId = patron.getPatronId();

        if (patrons.containsKey(patronId)) {
            throw new IllegalArgumentException(
                    "A patron with this ID already exists: " + patronId
            );
        }

        patrons.put(patronId, patron);
    }

    public Patron getPatronById(String patronId) {
        validatePatronId(patronId);

        Patron patron = patrons.get(patronId);

        if (patron == null) {
            throw new IllegalArgumentException(
                    "Patron not found: " + patronId
            );
        }

        return patron;
    }

    public void updatePatronInfo(
            String patronId,
            String name,
            String email
    ) {
        validateText(name, "Patron name");
        validateText(email, "Patron email");

        Patron patron = getPatronById(patronId);
        patron.setName(name);
        patron.setEmail(email);
    }

    public void removePatron(String patronId, List<Loan> loans) {
        Patron patron = getPatronById(patronId);

        if (loans == null) {
            throw new IllegalArgumentException("Loan list cannot be null.");
        }

        for (Loan loan : loans) {
            boolean belongsToPatron = loan.getPatron()
                    .getPatronId().equals(patronId);

            if (belongsToPatron && loan.getReturnDate() == null) {
                throw new IllegalStateException(
                        "Patron has an active loan."
                );
            }
        }

        patrons.remove(patron.getPatronId());
    }

    public List<Patron> getAllPatrons() {
        return new ArrayList<>(patrons.values());
    }

    private void validatePatronId(String patronId) {
        validateText(patronId, "Patron ID");
    }

    private void validateText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " cannot be null or blank."
            );
        }
    }
}
