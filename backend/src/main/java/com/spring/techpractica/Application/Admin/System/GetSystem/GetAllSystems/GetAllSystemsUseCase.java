package com.spring.techpractica.Application.Admin.System.GetSystem.GetAllSystems;

import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.System.Entity.System;
import com.spring.techpractica.Core.System.SystemRepository;
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
