package com.spring.techpractica.Application.Admin.System.CreateSystem;

import com.spring.techpractica.Core.Shared.Exception.ResourcesDuplicateException;
import com.spring.techpractica.Core.System.Entity.System;
import com.spring.techpractica.Core.System.SystemFactory;
import com.spring.techpractica.Core.System.SystemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CreateSystemUseCase {
    private final SystemRepository systemRepository;
    private final SystemFactory systemFactory;

    @Transactional
    public System execute(CreateSystemCommand command) {
        String name = command.name();
        if (systemRepository.existsByName(name)) {
            throw new ResourcesDuplicateException(name);
        }
        System system = systemFactory.create(name);
        return systemRepository.save(system);
    }
}
