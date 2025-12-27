package com.spring.techpractica.application.session.get.sessions.count;

import com.spring.techpractica.core.session.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetSessionsCountUseCase {
    private final SessionRepository sessionRepository;

    public long execute() {
        return sessionRepository.getSessionsCount();
    }
}
