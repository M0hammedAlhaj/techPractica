package com.spring.techpractica.application.session.get.user.sessions.count;

import com.spring.techpractica.core.session.SessionRepository;
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
