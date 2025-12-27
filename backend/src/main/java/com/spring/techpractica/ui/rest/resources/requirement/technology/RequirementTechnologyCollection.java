package com.spring.techpractica.ui.rest.resources.requirement.technology;

import com.fasterxml.jackson.annotation.JsonValue;
import com.spring.techpractica.core.requirement.technology.Entity.RequirementTechnology;
import lombok.Getter;

import java.util.List;

@Getter
public class RequirementTechnologyCollection {

    @JsonValue
    private final List<RequirementTechnologyResources> requirementTechnologies;

    public RequirementTechnologyCollection(List<RequirementTechnology> requirementTechnologies) {
        this.requirementTechnologies = requirementTechnologies.stream()
                .map(RequirementTechnologyResources::new).toList();
    }
}
