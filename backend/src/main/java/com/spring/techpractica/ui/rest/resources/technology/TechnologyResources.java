package com.spring.techpractica.ui.rest.resources.technology;

import com.spring.techpractica.core.technology.entity.Technology;
import com.spring.techpractica.ui.rest.resources.field.FieldCollection;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class TechnologyResources {

    private final UUID id;
    private final String name;
    private final FieldCollection fields;

    public TechnologyResources(Technology technology) {
        this.id = technology.getId();
        this.name = technology.getName();
        this.fields = new FieldCollection(technology.getFields());
    }
}