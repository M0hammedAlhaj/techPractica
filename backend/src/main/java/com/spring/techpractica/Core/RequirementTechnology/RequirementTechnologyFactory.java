package com.spring.techpractica.Core.RequirementTechnology;

import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import com.spring.techpractica.Core.RequirementTechnology.Entity.RequirementTechnology;
import com.spring.techpractica.Core.RequirementTechnology.Model.RequirementTechnologyId;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import org.springframework.stereotype.Component;

@Component
public class RequirementTechnologyFactory {
    public RequirementTechnology create(Requirement requirement, Technology technology) {
        return RequirementTechnology.builder()
                .id(new RequirementTechnologyId(requirement.getId(), technology.getId()))
                .requirement(requirement)
                .technology(technology)
                .build();
    }
}
