package com.spring.techpractica.Application.Session.update;

import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Field.FieldRepository;
import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import com.spring.techpractica.Core.Requirement.RequirementFactory;
import com.spring.techpractica.Core.RequirementTechnology.RequirementTechnologyFactory;
import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.Session.SessionRepository;
import com.spring.techpractica.Core.SessionMembers.model.Role;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.Shared.Exception.UnauthorizedActionException;
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
public class UpdateSessionUseCase {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final SystemRepository systemRepository;
    private final FieldRepository fieldRepository;
    private final RequirementFactory requirementFactory;
    private final TechnologyRepository technologyRepository;
    private final RequirementTechnologyFactory requirementTechnologyFactory;

    @Transactional
    public Session execute(UpdateSessionCommand command) {
        User user = userRepository.getOrThrowByID(command.userId());
        Session session = sessionRepository.getOrThrowByID(command.sessionId());

        validateSessionOwner(session, user);

        session.addBasicInfo(command.name(),command.description(),command.isPrivate());
        updateSystem(session, command.system());
        updateRequirementsForSession(session, command);

        return sessionRepository.save(session);
    }

    private void validateSessionOwner(Session session, User user) {
        boolean isOwner = session.getMembers().stream()
                .anyMatch(member -> member.getUser().getId().equals(user.getId())
                        && member.getRole() == Role.OWNER);

        if (!isOwner) {
            throw new UnauthorizedActionException("User must be the session owner to update it");
        }
    }

    private void updateSystem(Session session, UUID systemId) {
        System system = systemRepository.getOrThrowByID(systemId);
        session.addSystem(system);
    }

    private void updateRequirementsForSession(Session session, UpdateSessionCommand command) {

        session.getRequirements().clear();

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
