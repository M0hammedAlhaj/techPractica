package com.spring.techpractica.maper;

import com.spring.techpractica.dto.SystemResponse;
import com.spring.techpractica.model.entity.techSkills.System;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SystemMapper {


    private SystemMapper() {
    }

    public static SystemResponse systemToSystemResponse(System system) {

        return SystemResponse
                .builder()
                .systemName(system.getSystemName())
                .build();

    }

    public static List<SystemResponse> systemsToSystemResponseList(List<System> systems) {
        return systems.
                stream()
                .map(SystemMapper::systemToSystemResponse)
                .toList();
    }

}
