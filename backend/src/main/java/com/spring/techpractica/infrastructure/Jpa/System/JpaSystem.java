package com.spring.techpractica.infrastructure.Jpa.System;

import com.spring.techpractica.Core.System.Entity.System;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaSystem extends JpaRepository<System, UUID> {
    boolean existsByName(String name);

    List<System> findAllByNameIn(Collection<String> names);

    Optional<System> findSystemByName(String system);
}
