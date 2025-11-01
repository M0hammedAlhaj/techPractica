package com.spring.techpractica.application.session.get.user.sessions;

import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetUserSessionsUseCase {

    private final SessionRepository sessionRepository;

    public Page<Session> execute(GetUserSessionCommand command) {
        return sessionRepository.getSessionsByUser(command.userId(), PageRequest.of(command.page(), command.size()));
    }
}