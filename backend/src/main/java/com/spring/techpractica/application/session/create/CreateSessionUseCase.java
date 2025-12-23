package com.spring.techpractica.application.session.create;

import com.spring.techpractica.application.session.create.github.repo.CreateGithubRepositoryUseCase;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.SessionFactory;
import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.service.AddRequirementsForSessionService;
import com.spring.techpractica.core.session.members.Entity.SessionMember;
import com.spring.techpractica.core.session.members.SessionMembersFactory;
import com.spring.techpractica.core.session.members.model.Role;
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
public class CreateSessionUseCase {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final SessionFactory sessionFactory;
    private final SessionMembersFactory sessionMembersFactory;
    private final SystemRepository systemRepository;
    private final AddRequirementsForSessionService requirementsForSession;
    private final CreateGithubRepositoryUseCase createGithubRepositoryUseCase;

    @Transactional
    public Session execute(CreateSessionCommand command) {
        User owner = userRepository.getOrThrowByID(command.userId());

        Session session = sessionFactory.create(command);
        session = sessionRepository.save(session);

        addOwner(session, owner);
        session.addBasicInfo(session.getName(), session.getDescription(), session.isPrivate());
        addSystem(session, command.system());

        requirementsForSession.addRequirementsForSession(session,command);

        createGithubRepositoryUseCase.createRepository(owner.getGithubAccessToken(), command.name(), command.isPrivate());

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
}