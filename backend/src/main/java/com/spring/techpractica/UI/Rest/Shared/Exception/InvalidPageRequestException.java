package com.spring.techpractica.UI.Rest.Shared.Exception;

public class InvalidPageRequestException extends RuntimeException {
    public InvalidPageRequestException(String message) {
        super(message);
    }

    public InvalidPageRequestException(int page, int size) {
        super(String.format("Invalid page request: page=%d, size=%d. " +
                            "Page number must be >= 0 and size > 0.", page, size));
    }
}
