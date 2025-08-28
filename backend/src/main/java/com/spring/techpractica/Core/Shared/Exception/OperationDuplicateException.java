package com.spring.techpractica.Core.Shared.Exception;

public class OperationDuplicateException extends RuntimeException {

    public OperationDuplicateException(String operationName) {
        super(String.format("Operation already exists or was already executed: %s", operationName));
    }

}
