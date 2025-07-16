package com.spring.techpractica.mengmentData;

import com.spring.techpractica.exception.ResourcesNotFoundException;
import com.spring.techpractica.maper.SystemMapper;
import com.spring.techpractica.model.entity.techSkills.System;
import com.spring.techpractica.repository.SystemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SystemManagementData {

    private final SystemRepository systemRepository;
    private final SystemMapper systemMapper;


    public List<System> getAllSystems() {
        return systemRepository.findAll();
    }

    public System getSystemByName(String name) {
        return systemRepository.findById(name)
                .orElseThrow(() -> new ResourcesNotFoundException("Systems no concentrate"));
    }
}
