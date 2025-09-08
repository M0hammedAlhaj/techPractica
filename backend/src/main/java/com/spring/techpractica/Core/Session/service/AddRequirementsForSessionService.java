package com.spring.techpractica.Core.Session.service;

import com.spring.techpractica.Application.Session.create.CreateSessionCommand;
import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Field.FieldRepository;
import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import com.spring.techpractica.Core.Requirement.RequirementFactory;
import com.spring.techpractica.Core.RequirementTechnology.RequirementTechnologyFactory;
import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import com.spring.techpractica.Core.Technology.TechnologyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AddRequirementsForSessionService {

    private final FieldRepository fieldRepository;
    private final RequirementFactory requirementFactory;
    private final TechnologyRepository technologyRepository;
    private final RequirementTechnologyFactory requirementTechnologyFactory;

    public void addRequirementsForSession(Session session, CreateSessionCommand command) {
        command.requirements().forEach(requirementRequest -> {
            Requirement requirement = createRequirement(session, requirementRequest.getFieldId());
            session.addRequirement(requirement);

            List<Technology> technologies = fetchTechnologies(requirementRequest.getTechnologies());
            addTechnologiesToRequirement(requirement, technologies);
        });
    }

    private Requirement createRequirement(Session session, UUID fieldId) {
        Field field = fieldRepository.getOrThrowByID(fieldId);
        return requirementFactory.create(session, field);
    }

    private List<Technology> fetchTechnologies(List<UUID> technologyIds) {
        List<Technology> technologies = technologyRepository.findAllByIds(new HashSet<>(technologyIds));
        if (technologies.size() != technologyIds.size()) {
            throw new ResourcesNotFoundException(technologyIds.toString());
        }
        return technologies;
    }

    private void addTechnologiesToRequirement(Requirement requirement, List<Technology> technologies) {
        technologies.stream()
                .map(tech -> requirementTechnologyFactory.create(requirement, tech))
                .forEach(requirement::addRequirementTechnology);
    }
}
