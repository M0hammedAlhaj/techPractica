package com.spring.techpractica.application.session.update;

import com.spring.techpractica.application.session.create.CreateSessionCommand;
import com.spring.techpractica.core.field.FieldRepository;
import com.spring.techpractica.core.field.entity.Field;
import com.spring.techpractica.core.request.RequestRepository;
import com.spring.techpractica.core.requirement.RequirementFactory;
import com.spring.techpractica.core.requirement.RequirementRepository;
import com.spring.techpractica.core.requirement.entity.Requirement;
import com.spring.techpractica.core.requirement.technology.RequirementTechnologyFactory;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.service.AddRequirementsForSessionService;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.shared.Exception.UnauthorizedActionException;
import com.spring.techpractica.core.system.entity.System;
import com.spring.techpractica.core.system.SystemRepository;
import com.spring.techpractica.core.technology.TechnologyRepository;
import com.spring.techpractica.core.technology.entity.Technology;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UpdateSessionUseCase {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final SystemRepository systemRepository;
    private final AddRequirementsForSessionService requirementsForSessionService;
    private final RequirementRepository requirementRepository;
    private final FieldRepository fieldRepository;
    private final RequirementFactory requirementFactory;
    private final TechnologyRepository  technologyRepository;
    private final RequirementTechnologyFactory requirementTechnologyFactory;
    private final RequestRepository requestRepository;

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
        boolean isOwner = session.isOwner(user.getId());

        if (!isOwner) {
            throw new UnauthorizedActionException("User must be the session owner to update it");
        }
    }

    private void updateSystem(Session session, UUID systemId) {
        System system = systemRepository.getOrThrowByID(systemId);
        session.addSystem(system);
    }

    private void updateRequirementsForSession(Session session, UpdateSessionCommand command) {
        List<Requirement> existingReqList = requirementRepository.findBySessionId(session.getId());
        Map<UUID, Requirement> existingFieldAndReq = existingReqList.stream().collect(
                Collectors.toMap(req -> req.getField().getId(), req -> req));

        command.requirements().forEach(reqCommand -> {
            UUID fieldId = reqCommand.getFieldId();
            List<Technology> technologies = fetchTechnologies(reqCommand.getTechnologies());

            if (existingFieldAndReq.containsKey(fieldId)) {
                Requirement existingReq = existingFieldAndReq.get(fieldId);
                existingReq.clearRequirementTechnologies();
                technologies.forEach(tech ->
                        existingReq.addRequirementTechnology(requirementTechnologyFactory.create(existingReq, tech))
                );
                existingFieldAndReq.remove(fieldId);
            } else {
                Requirement newReq = createRequirement(session, fieldId);
                session.addRequirement(newReq);
                technologies.forEach(tech ->
                        newReq.addRequirementTechnology(requirementTechnologyFactory.create(newReq, tech))
                );
            }
        });

        for (Requirement oldReq : existingFieldAndReq.values()) {
            boolean hasRequests = requestRepository.existsByRequirementId(oldReq.getId());

            if (hasRequests) {
                requestRepository.deleteAllByRequirementId(oldReq.getId());
            }
            requirementRepository.delete(oldReq);
        }
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
}