package com.spring.techpractica.application.admin.system.delete;

import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.shared.Exception.ResourcesDuplicateException;
import com.spring.techpractica.core.system.entity.System;
import com.spring.techpractica.core.system.SystemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DeleteSystemUseCase {
    private final SystemRepository systemRepository;

    private final SessionRepository sessionRepository;

    @Transactional
    public void execute(DeleteSystemCommand command) {

        System system = systemRepository.getOrThrowByID(command.systemId());

        if(!sessionRepository.getSessionsBySystemId(command.systemId()).isEmpty()) {
            throw new ResourcesDuplicateException("session with system id " + command.systemId() + "  exists");
        }
         systemRepository.deleteById(system.getId());
    }
}
