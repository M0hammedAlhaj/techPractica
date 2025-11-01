package com.spring.techpractica.ui.rest.resources.requirement;

import com.spring.techpractica.core.requirement.entity.Requirement;
import com.spring.techpractica.ui.rest.resources.requirement.technology.RequirementTechnologyCollection;
import lombok.Getter;

import java.util.UUID;

@Getter
public class RequirementResources {

    private final UUID requirementId;
    private final String field;
    private final RequirementTechnologyCollection technologies;

    public RequirementResources(Requirement requirement) {
        this.field = requirement.getField().getName();
        this.technologies =
                new RequirementTechnologyCollection(requirement.getRequirementTechnologies());
        this.requirementId = requirement.getId();
    }
}
