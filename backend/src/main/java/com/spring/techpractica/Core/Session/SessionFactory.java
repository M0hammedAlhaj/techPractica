package com.spring.techpractica.Core.Session;

import com.spring.techpractica.Application.Session.create.CreateSessionCommand;
import com.spring.techpractica.Core.Session.Entity.Session;
import org.springframework.stereotype.Component;

@Component
public class SessionFactory {

    public Session create(CreateSessionCommand command) {

        return Session.builder()
                .name(command.name())
                .description(command.description())
                .isPrivate(command.isPrivate())
                .isRunning(false)
                .build();
    }
}
