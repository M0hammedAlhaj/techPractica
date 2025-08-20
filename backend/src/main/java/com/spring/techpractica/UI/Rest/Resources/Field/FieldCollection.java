package com.spring.techpractica.UI.Rest.Resources.Field;

import com.fasterxml.jackson.annotation.JsonValue;
import com.spring.techpractica.Core.Field.Entity.Field;
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
