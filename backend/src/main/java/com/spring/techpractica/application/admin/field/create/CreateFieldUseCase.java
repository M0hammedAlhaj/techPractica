package com.spring.techpractica.application.admin.field.create;

import com.spring.techpractica.core.field.entity.Field;
import com.spring.techpractica.core.field.FieldFactory;
import com.spring.techpractica.core.field.FieldRepository;
import com.spring.techpractica.core.shared.Exception.ResourcesDuplicateException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CreateFieldUseCase {
    private final FieldRepository fieldRepository;
    private final FieldFactory fieldFactory;

    @Transactional
    public Field execute(CreateFieldCommand command) {
        String name = command.name();
        if (fieldRepository.existsByName(name)) {
            throw new ResourcesDuplicateException(name);
        }
        Field field = fieldFactory.create(name);
        return fieldRepository.save(field);
    }
}
