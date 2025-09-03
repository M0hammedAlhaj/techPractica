package com.spring.techpractica.Application.Session.create;

import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Field.FieldRepository;
import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import com.spring.techpractica.Core.Requirement.RequirementFactory;
import com.spring.techpractica.Core.RequirementTechnology.RequirementTechnologyFactory;
import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.Session.SessionFactory;
import com.spring.techpractica.Core.Session.SessionRepository;
import com.spring.techpractica.Core.SessionMembers.Entity.SessionMember;
import com.spring.techpractica.Core.SessionMembers.SessionMembersFactory;
import com.spring.techpractica.Core.SessionMembers.model.Role;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.System.Entity.System;
import com.spring.techpractica.Core.System.SystemRepository;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import com.spring.techpractica.Core.Technology.TechnologyRepository;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CreateSessionUseCase {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final SessionFactory sessionFactory;
    private final SessionMembersFactory sessionMembersFactory;
    private final SystemRepository systemRepository;
    private final FieldRepository fieldRepository;
    private final RequirementFactory requirementFactory;
    private final TechnologyRepository technologyRepository;
    private final RequirementTechnologyFactory requirementTechnologyFactory;

    @Transactional
    public Session execute(CreateSessionCommand command) {
        User owner = userRepository.getOrThrowByID(command.userId());

        Session session = sessionFactory.create(command);
        session = sessionRepository.save(session);

        addOwner(session, owner);
        session.addBasicInfo(session.getName(), session.getDescription(), session.isPrivate());
        addSystem(session, command.system());
        addRequirementsForSession(session, command);

        return sessionRepository.save(session);
    }

    private void addOwner(Session session, User owner) {
        SessionMember sessionMember = sessionMembersFactory.create(session, owner, Role.OWNER);
        session.addMember(sessionMember);
    }

    private void addSystem(Session session, UUID systemId) {
        System system = systemRepository.getOrThrowByID(systemId);
        session.addSystem(system);
    }

    private void addRequirementsForSession(Session session, CreateSessionCommand command) {
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