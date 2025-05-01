package com.spring.techpractica.service.techSkills.Field;

import com.spring.techpractica.dto.techSkills.FieldTransfer;
import com.spring.techpractica.mengmentData.FieldManagementData;
import com.spring.techpractica.model.entity.techSkills.Field;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class FieldService {

    private final FieldManagementData fieldManagementData;

    public List<Field> getAllFields() {
        return fieldManagementData.getAllFields();
    }

    public void addField(Field field) { fieldManagementData.save(field); }
}
