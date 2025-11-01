package com.spring.techpractica.application.session.update;

import com.spring.techpractica.application.session.create.CreateSessionCommand;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.service.AddRequirementsForSessionService;
import com.spring.techpractica.core.shared.Exception.UnauthorizedActionException;
import com.spring.techpractica.core.system.entity.System;
import com.spring.techpractica.core.system.SystemRepository;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UpdateSessionUseCase {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final SystemRepository systemRepository;
    private final AddRequirementsForSessionService requirementsForSessionService;

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

        session.clearRequirements();

        requirementsForSessionService.addRequirementsForSession(session, new CreateSessionCommand(
                command.userId(),
                command.name(),
                command.description(),
                command.isPrivate(),
                command.system(),
                command.requirements()
        ));
    }
}