package com.spring.techpractica.UI.Rest.Resources.Technology;

import com.spring.techpractica.Core.Technology.Entity.Technology;
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
