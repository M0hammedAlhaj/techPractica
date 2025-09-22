package com.spring.techpractica.Application.Session.GetSessions.UserSessions;

import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.Session.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GetUserSessionsUseCase {

    private final SessionRepository sessionRepository;

    public List<Session> execute(UUID userId) {
        return sessionRepository.getSessionsByUser(userId);
    }
}