package com.example.Book.Managment.exception;

public class BookReviewNotFoundException extends RuntimeException {
    public BookReviewNotFoundException(String message) {
        super(message);
    }
}
