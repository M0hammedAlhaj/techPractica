package com.spring.techpractica.UI.Rest.Resources.RequirementTechnology;

import com.fasterxml.jackson.annotation.JsonValue;
import com.spring.techpractica.Core.RequirementTechnology.Entity.RequirementTechnology;
import lombok.Getter;

@Getter
public class RequirementTechnologyResources {

    @JsonValue
    private final String name;

    public RequirementTechnologyResources(RequirementTechnology requirement) {
        this.name = requirement.getTechnology().getName();
    }
}