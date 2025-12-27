package com.spring.techpractica.infrastructure.jpa.field;

import com.spring.techpractica.core.field.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaField extends JpaRepository<Field, UUID> {
    boolean existsByName(String name);

    List<Field> findAllByNameIn(Collection<String> names);

    Optional<Field> findFieldByName(String fieldName);

    void deleteById(UUID id);
}
