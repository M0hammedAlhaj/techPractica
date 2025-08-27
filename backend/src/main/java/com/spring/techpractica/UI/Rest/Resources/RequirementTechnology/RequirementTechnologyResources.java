package com.spring.techpractica.UI.Rest.Resources.RequirementTechnology;

import com.spring.techpractica.Core.RequirementTechnology.Entity.RequirementTechnology;
import lombok.Getter;

@Getter
public class RequirementTechnologyResources {
    private final String name;

    public RequirementTechnologyResources(RequirementTechnology requirement) {
        this.name = requirement.getTechnology().getName();
    }
}