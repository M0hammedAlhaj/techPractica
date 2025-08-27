package com.spring.techpractica.Core.System;

import com.spring.techpractica.Core.System.Entity.System;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SystemRepository extends JpaRepository<com.spring.techpractica.Core.System.Entity.System, UUID> {
    boolean existsByName(String name);

    List<com.spring.techpractica.Core.System.Entity.System> findAllByNameIn(Collection<String> names);

    Optional<System> findSystemByName(String system);
}
