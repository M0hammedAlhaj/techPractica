package com.spring.techpractica.core.field;

import com.spring.techpractica.core.field.entity.Field;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FieldFactory {

    public Field create(String name) {


        return Field.builder()
                .name(name)
                .build();
    }
}
