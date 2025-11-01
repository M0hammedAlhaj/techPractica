package com.spring.techpractica.core.session;

import com.spring.techpractica.application.session.create.CreateSessionCommand;
import com.spring.techpractica.core.session.entity.Session;
import org.springframework.stereotype.Component;

@Component
public class SessionFactory {

    public Session create(CreateSessionCommand command) {

        return Session.builder()
                .name(command.name())
                .description(command.description())
                .isPrivate(command.isPrivate())
                .status(SessionStatus.WAITING)
                .build();
    }
}
