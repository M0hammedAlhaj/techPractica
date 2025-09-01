package com.spring.techpractica.infrastructure.Jpa.Field;

import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Field.FieldRepository;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaFieldRepository implements FieldRepository {
    private final JpaField jpaField;

    @Override
    public Field save(Field field) {
        return jpaField.save(field);
    }

    @Override
    public Field update(Field field) {
        UUID fieldId = field.getId();
        if (jpaField.existsById(fieldId)) {
            return jpaField.save(field);
        }
        throw new ResourcesNotFoundException(fieldId);
    }

    @Override
    public Optional<Field> findById(UUID id) {
        return jpaField.findById(id);
    }


    @Override
    public boolean existsByName(String name) {
        return jpaField.existsByName(name);
    }

    @Override
    public List<Field> findAllByNames(Collection<String> names) {
        return jpaField.findAllByNameIn(names);
    }

    @Override
    public Optional<Field> findFieldByName(String fieldName) {
        return jpaField.findFieldByName(fieldName);
    }

    @Override
    public List<Field> findAll() {
        return jpaField.findAll();
    }
}
