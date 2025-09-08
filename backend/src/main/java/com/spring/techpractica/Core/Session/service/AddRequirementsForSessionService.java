package com.spring.techpractica.Core.Session.service;

import com.spring.techpractica.Application.Session.create.CreateSessionCommand;
import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Field.FieldRepository;
import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import com.spring.techpractica.Core.Requirement.RequirementFactory;
import com.spring.techpractica.Core.RequirementTechnology.RequirementTechnologyFactory;
import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.Session.SessionFactory;
import com.spring.techpractica.Core.Session.SessionRepository;
import com.spring.techpractica.Core.SessionMembers.SessionMembersFactory;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.System.SystemRepository;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import com.spring.techpractica.Core.Technology.TechnologyRepository;
import com.spring.techpractica.Core.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class AddRequirementsForSessionService {

    private final FieldRepository fieldRepository;
    private final RequirementFactory requirementFactory;
    private final TechnologyRepository technologyRepository;
    private final RequirementTechnologyFactory requirementTechnologyFactory;

    public void addRequirementsForSession(Session session, CreateSessionCommand command) {
        for (var requirementRequest : command.requirements()) {
            Field field = fieldRepository.getOrThrowByID(requirementRequest.getFieldId());

            Requirement requirement = requirementFactory.create(session, field);
            session.addRequirement(requirement);

            List<Technology> technologies = technologyRepository
                    .findAllByIds(new HashSet<>(requirementRequest.getTechnologies()));

            if (technologies.size() != requirementRequest.getTechnologies().size()) {
                throw new ResourcesNotFoundException(requirementRequest.getTechnologies().toString());
            }

            technologies.stream()
                    .map(tech -> requirementTechnologyFactory.create(requirement, tech))
                    .forEach(requirement::addRequirementTechnology);
        }
    }
}