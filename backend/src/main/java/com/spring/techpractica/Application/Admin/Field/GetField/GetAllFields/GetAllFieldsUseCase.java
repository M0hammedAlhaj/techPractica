package com.spring.techpractica.Application.Admin.Field.GetField.GetAllFields;

import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Field.FieldRepository;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
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
