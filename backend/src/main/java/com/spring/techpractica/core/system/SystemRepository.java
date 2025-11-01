package com.spring.techpractica.core.system;

import com.spring.techpractica.core.shared.BaseRepository;
import com.spring.techpractica.core.system.entity.System;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SystemRepository extends BaseRepository<System> {
    boolean existsByName(String name);

    List<System> findAllByNames(Collection<String> names);

    Optional<System> findSystemByName(String system);

    List<System> findAll();

    void deleteById(UUID id);
}