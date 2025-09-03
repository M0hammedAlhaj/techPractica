package com.spring.techpractica.Core.Session.service;

import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.System.Entity.System;
import com.spring.techpractica.Core.System.SystemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AddSystemToSessionService {

    private final SystemRepository systemRepository;

    private void addSystem(Session session, UUID systemId) {
        System system = systemRepository.getOrThrowByID(systemId);
        session.addSystem(system);
    }
}