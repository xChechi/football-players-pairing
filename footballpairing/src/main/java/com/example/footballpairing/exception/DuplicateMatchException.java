package com.example.footballpairing.exception;

public class DuplicateMatchException extends RuntimeException {
    public DuplicateMatchException(String message) {
        super(message);
    }
}
