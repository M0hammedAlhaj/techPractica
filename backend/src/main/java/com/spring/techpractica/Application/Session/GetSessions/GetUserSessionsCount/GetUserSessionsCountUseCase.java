package com.spring.techpractica.Application.Session.GetSessions.GetUserSessionsCount;

import com.spring.techpractica.Core.Session.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetUserSessionsCountUseCase {
    private final SessionRepository sessionRepository;

    public long execute(GetUserSessionsCountCommand command) {
        return sessionRepository.getUserSessionsCount(command.userId());
    }
}
