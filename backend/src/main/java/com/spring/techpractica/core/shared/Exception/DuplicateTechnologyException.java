package com.spring.techpractica.core.shared.Exception;

public class DuplicateTechnologyException extends RuntimeException {
    public DuplicateTechnologyException(String fieldName) {
        super("Duplicate technologies found for field: " + fieldName);
    }
}