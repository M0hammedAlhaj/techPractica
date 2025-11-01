package com.spring.techpractica.core.requirement.technology;

import com.spring.techpractica.core.requirement.entity.Requirement;
import com.spring.techpractica.core.requirement.technology.Entity.RequirementTechnology;
import com.spring.techpractica.core.requirement.technology.Model.RequirementTechnologyId;
import com.spring.techpractica.core.technology.entity.Technology;
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
