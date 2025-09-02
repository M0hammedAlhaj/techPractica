package com.spring.techpractica.UI.Rest.Resources.Technology;

import com.spring.techpractica.Core.Technology.Entity.Technology;
import com.spring.techpractica.UI.Rest.Resources.Field.FieldCollection;
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