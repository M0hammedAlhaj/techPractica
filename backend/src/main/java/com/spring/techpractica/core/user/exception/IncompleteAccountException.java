package com.spring.techpractica.core.user.exception;

public class IncompleteAccountException extends RuntimeException {

    public IncompleteAccountException(String message) {
        super(message);
    }
}
