package com.spring.techpractica.core.shared.Exception;

import java.util.List;
import java.util.UUID;

public class ResourcesNotFoundException extends RuntimeException {
    public ResourcesNotFoundException(UUID uuid) {
        super("Resource not found with id: " + uuid.toString());
    }

    public ResourcesNotFoundException(String name) {
        super("Resource not found: "+name);
    }

    public ResourcesNotFoundException(List<String> names) {
        super("Resources not found: " + names.toString());
    }


}