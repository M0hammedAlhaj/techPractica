package com.spring.techpractica.Core.Shared.Exception;

import java.util.UUID;

public class ResourcesDuplicateException extends RuntimeException {
    public ResourcesDuplicateException(UUID id) {
        super(String.format("Entity has already been created with id: %s", id));
    }
}
