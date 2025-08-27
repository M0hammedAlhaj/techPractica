package com.spring.techpractica.Core.Field;

import com.spring.techpractica.Core.Field.Entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FieldRepository extends JpaRepository<Field, UUID> {
    boolean existsByName(String name);

    List<Field> findAllByNameIn(Collection<String> names);

    Optional<Field> findFieldByName(String fieldName);
}
