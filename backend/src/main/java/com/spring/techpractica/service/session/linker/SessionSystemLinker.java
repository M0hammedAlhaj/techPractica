package com.spring.techpractica.service.session.linker;

import com.spring.techpractica.mengmentData.SystemManagementData;
import com.spring.techpractica.model.entity.Session;
import com.spring.techpractica.model.entity.techSkills.System;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SessionSystemLinker {

    private final SystemManagementData systemManagementData;

    public void linkSystemToSession(Session session, String systemName) {
        System system = systemManagementData.getSystemByName(systemName);
        session.getSessionSystems().add(system);
    }
}
