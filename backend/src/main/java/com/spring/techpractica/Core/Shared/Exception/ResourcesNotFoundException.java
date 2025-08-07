package com.spring.techpractica.Core.Shared.Exception;

import java.util.UUID;

public class ResourcesNotFoundException extends RuntimeException {
    public ResourcesNotFoundException(String message) {
        super(message);
    }

    public ResourcesNotFoundException(UUID uuid) {
        super("Resource not found with id: " + uuid.toString());
    }
}
