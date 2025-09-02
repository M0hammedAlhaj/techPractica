package com.spring.techpractica.UI.Rest.Resources.Requirment;

import com.fasterxml.jackson.annotation.JsonValue;
import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import lombok.Getter;

import java.util.List;

@Getter
public class RequirementCollection {

    @JsonValue
    private final List<RequirementResources> requirements;

    public RequirementCollection(List<Requirement> requirements) {
        this.requirements = requirements.stream()
                .map(RequirementResources::new)
                .toList();
    }
}