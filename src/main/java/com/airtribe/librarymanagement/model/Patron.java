package com.airtribe.librarymanagement.model;

public class Patron {
    private String patronId;
    private String name;
    private String email;

    public Patron(String patronId, String name, String email) {
        validateText(patronId,"PatronID");
        validateText(name,"Name");
        validateText(email,"Email");
        this.patronId = patronId;
        this.name = name;
        this.email = email;
    }

    private void validateText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " cannot be null or blank."
            );
        }
    }

    public String getPatronId() {
        return patronId;
    }

    public void setPatronId(String patronId) {
        this.patronId = patronId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
