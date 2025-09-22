package com.spring.techpractica.Core.Shared;

import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface BaseRepository<T> {
    T save(T t);

    T update(T t);

    Optional<T> findById(UUID id);

    default T getOrThrowByID(UUID id){
        return findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException(id));
    }
}
