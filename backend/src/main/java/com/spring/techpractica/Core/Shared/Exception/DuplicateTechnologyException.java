package com.spring.techpractica.Core.Shared.Exception;

public class DuplicateTechnologyException extends RuntimeException {
    public DuplicateTechnologyException(String fieldName) {
        super("Duplicate technologies found for field: " + fieldName);
    }
}