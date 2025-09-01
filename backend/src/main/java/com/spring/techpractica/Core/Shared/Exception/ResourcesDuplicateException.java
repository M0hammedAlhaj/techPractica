package com.spring.techpractica.Core.Shared.Exception;

import java.util.UUID;

public class ResourcesDuplicateException extends RuntimeException {

    public ResourcesDuplicateException(String name) {
        super(String.format("Entity has already been created with name: %s", name));
    }
  
}
