package com.spring.techpractica.ui.rest.resources.requirement.technology;

import com.fasterxml.jackson.annotation.JsonValue;
import com.spring.techpractica.core.requirement.technology.Entity.RequirementTechnology;
import lombok.Getter;

@Getter
public class RequirementTechnologyResources {

    @JsonValue
    private final String name;

    public RequirementTechnologyResources(RequirementTechnology requirement) {
        this.name = requirement.getTechnology().getName();
    }
}