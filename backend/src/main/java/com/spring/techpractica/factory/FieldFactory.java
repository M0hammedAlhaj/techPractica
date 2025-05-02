package com.spring.techpractica.factory;

import com.spring.techpractica.model.entity.techSkills.Field;
import org.springframework.stereotype.Component;

@Component
public class FieldFactory {

    public FieldFactory(){}

    public static Field createField(String fieldName){
        return Field.builder()
                .fieldName(fieldName)
                .build();
    }
}
