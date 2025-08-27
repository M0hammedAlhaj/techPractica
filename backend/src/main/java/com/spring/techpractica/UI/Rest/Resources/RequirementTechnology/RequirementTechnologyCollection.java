package com.spring.techpractica.UI.Rest.Resources.RequirementTechnology;

import com.fasterxml.jackson.annotation.JsonValue;
import com.spring.techpractica.Core.RequirementTechnology.Entity.RequirementTechnology;
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
