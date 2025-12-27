package com.spring.techpractica.application.admin.system.get.all;

import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.system.entity.System;
import com.spring.techpractica.core.system.SystemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllSystemsUseCase {
    private final SystemRepository systemRepository;

    public List<System> execute(GetAllSystemsCommand command) {
        List<System> systems = systemRepository.findAll();

        if (systems.isEmpty()) {
            throw new ResourcesNotFoundException("No systems found");
        }

        return systems;
    }
}
