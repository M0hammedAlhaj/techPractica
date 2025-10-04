package com.spring.techpractica.Application.Admin.System.UpdateSystem;

import com.spring.techpractica.Core.Shared.Exception.ResourcesDuplicateException;
import com.spring.techpractica.Core.System.Entity.System;
import com.spring.techpractica.Core.System.SystemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UpdateSystemUseCase {
    private final SystemRepository systemRepository;

    @Transactional
    public System execute(UpdateSystemCommand command) {
        String name = command.name();
        System system =systemRepository.getOrThrowByID(command.systemId());

        if (systemRepository.existsByName(name)) {
            throw new ResourcesDuplicateException("System with name : " + name + " already exists");
        }
        system.setName(name);
        return systemRepository.save(system);
    }
}
