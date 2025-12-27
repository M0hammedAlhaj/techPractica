package com.spring.techpractica.core.session.service;

import com.spring.techpractica.application.session.create.CreateSessionCommand;
import com.spring.techpractica.core.field.entity.Field;
import com.spring.techpractica.core.field.FieldRepository;
import com.spring.techpractica.core.requirement.entity.Requirement;
import com.spring.techpractica.core.requirement.RequirementFactory;
import com.spring.techpractica.core.requirement.technology.RequirementTechnologyFactory;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.technology.entity.Technology;
import com.spring.techpractica.core.technology.TechnologyRepository;
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
