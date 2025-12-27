package com.spring.techpractica.ui.rest.resources.technology;

import com.spring.techpractica.core.technology.entity.Technology;
import lombok.Getter;

import java.util.UUID;

@Getter
public class TechnologySummary {
    private final String name;
    private final UUID id;
    public TechnologySummary(Technology technology) {
        this.name = technology.getName();
        this.id = technology.getId();
    }
}
