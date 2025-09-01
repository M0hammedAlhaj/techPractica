package com.spring.techpractica.UI.Rest.Resources.Requirment;

import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import com.spring.techpractica.UI.Rest.Resources.RequirementTechnology.RequirementTechnologyCollection;
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
