package com.spring.techpractica.application.admin.system.create;

import com.spring.techpractica.core.shared.Exception.ResourcesDuplicateException;
import com.spring.techpractica.core.system.entity.System;
import com.spring.techpractica.core.system.SystemFactory;
import com.spring.techpractica.core.system.SystemRepository;
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
