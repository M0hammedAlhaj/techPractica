package com.spring.techpractica.Core.Field;

import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Shared.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FieldRepository extends BaseRepository<Field> {
    boolean existsByName(String name);

    List<Field> findAllByNames(Collection<String> names);

    Optional<Field> findFieldByName(String fieldName);

    List<Field> findAll();
}
