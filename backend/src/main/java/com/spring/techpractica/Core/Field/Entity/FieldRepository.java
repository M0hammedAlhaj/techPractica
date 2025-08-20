package com.spring.techpractica.Core.Field.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface FieldRepository extends JpaRepository<Field, UUID> {
    boolean existsByName(String name);

    List<Field> findAllByNameIn(Collection<String> names);
}
