package com.spring.techpractica.Application.Session.CreateSession;

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
import com.spring.techpractica.Core.Technology.TechnologyRepository;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        User owner = userRepository.findById(command.userId())
                .orElseThrow(() -> new ResourcesNotFoundException(command.userId()));

        Session session = sessionFactory.create(command);
        session = sessionRepository.save(session);

        SessionMember sessionMember = sessionMembersFactory.create(session, owner, Role.OWNER);
        session.addMember(sessionMember);

        System system = systemRepository.findSystemByName(command.system())
                .orElseThrow(() -> new ResourcesNotFoundException(command.system()));
        session.addSystem(system);

        for (var requirementRequest : command.requirements()) {
            Field field = fieldRepository.findFieldByName(requirementRequest.getFieldName())
                    .orElseThrow(() -> new ResourcesNotFoundException(requirementRequest.getFieldName()));

            Requirement requirement = requirementFactory.create(session, field);
            session.addRequirement(requirement);

            requirementRequest.getTechnologies().stream()
                    .map(techName -> requirementTechnologyFactory.create(requirement,
                            technologyRepository.findTechnologyByName(techName)
                            .orElseThrow(() -> new ResourcesNotFoundException(techName))))
                    .forEach(requirement::addRequirementTechnology);
        }
        return sessionRepository.save(session);
    }
}