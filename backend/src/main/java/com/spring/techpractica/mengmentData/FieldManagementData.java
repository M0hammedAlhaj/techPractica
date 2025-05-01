package com.spring.techpractica.mengmentData;


import com.spring.techpractica.model.entity.techSkills.Field;
import com.spring.techpractica.repository.techSkills.FieldRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FieldManagementData {

    private final FieldRepository fieldRepository;


    public List<Field> getAllFields() {
        return fieldRepository.findAll();
    }

    public Optional<Field> findFieldByFieldName(String fieldName) {
        return fieldRepository.findById(fieldName);
    }

    public Field getFieldByFieldName(String fieldName) {
        return fieldRepository.findById(fieldName)
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));
    }

    public List<Field> getFieldsByFieldsName(List<String> fieldsName) {
        return fieldRepository.findAllById(fieldsName);
    }

    public void save(Field field) {
        fieldRepository.save(field);
    }
}
