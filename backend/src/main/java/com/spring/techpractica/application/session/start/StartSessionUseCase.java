package com.spring.techpractica.application.session.start;

import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.entity.Session;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StartSessionUseCase {

    private final SessionRepository sessionRepository;

    @Transactional
    public Session execute(StartSessionCommand command) {
        Session session = sessionRepository.getOrThrowByID(command.sessionId());

        session.runSession();

        return sessionRepository.save(session);
    }
}