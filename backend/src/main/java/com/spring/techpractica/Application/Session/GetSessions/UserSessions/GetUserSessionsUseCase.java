package com.spring.techpractica.Application.Session.GetSessions.UserSessions;

import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.Session.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GetUserSessionsUseCase {

    private final SessionRepository sessionRepository;

    public Page<Session> execute(GetUserSessionCommand command) {
        return sessionRepository.getSessionsByUser(command.userId(), PageRequest.of(command.page(), command.size()));
    }
}