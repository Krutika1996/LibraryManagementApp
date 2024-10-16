package com.example.librarymanagement.exception;

public class BookException extends RuntimeException {
    public BookException(String message) {
        super("Book not found");
    }
}

