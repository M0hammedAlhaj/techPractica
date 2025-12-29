package com.spring.techpractica.core.session;

public class SessionCannotBeFinishedException extends RuntimeException {
    public SessionCannotBeFinishedException(String message) {
        super(message);
    }
}
