package com.spring.techpractica.ui.rest.resources.field;

import com.fasterxml.jackson.annotation.JsonValue;
import com.spring.techpractica.core.field.entity.Field;
import lombok.*;

import java.util.List;

@Getter
public class FieldCollection {
    @JsonValue
    private final List<FieldResources> fields;

    public FieldCollection(List<Field> fields) {
        this.fields = fields.stream().map(field -> new FieldResources(field.getId(), field.getName())).toList();

    }
}
