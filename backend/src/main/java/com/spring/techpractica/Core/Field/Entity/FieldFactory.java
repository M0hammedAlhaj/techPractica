package com.spring.techpractica.Core.Field.Entity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class FieldFactory {

    public Field create(String name) {


        return Field.builder()
                .name(name)
                .build();
    }
}
