package com.spring.techpractica.Application.Admin.Field.UpdateField;

import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Field.FieldFactory;
import com.spring.techpractica.Core.Field.FieldRepository;
import com.spring.techpractica.Core.Shared.Exception.ResourcesDuplicateException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UpdateFieldUseCase {
    private final FieldRepository fieldRepository;

    @Transactional
    public Field execute(UpdateFieldCommand command) {
        String name = command.name();
        Field field =fieldRepository.getOrThrowByID(command.fieldId());

        if (fieldRepository.existsByName(name)) {
            throw new ResourcesDuplicateException("Field with name : " + name + " already exists");
        }
        field.setName(name);
        return fieldRepository.save(field);
    }
}
