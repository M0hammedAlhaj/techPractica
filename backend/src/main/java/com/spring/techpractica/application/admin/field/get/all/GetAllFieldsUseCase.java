package com.spring.techpractica.application.admin.field.get.all;

import com.spring.techpractica.core.field.entity.Field;
import com.spring.techpractica.core.field.FieldRepository;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllFieldsUseCase {
    private final FieldRepository fieldRepository;

    public List<Field> execute(GetAllFieldsCommand command) {
        List<Field> fields = fieldRepository.findAll();

        if (fields.isEmpty()) {
            throw new ResourcesNotFoundException("No fields found");
        }

        return fields;
    }
}
