package com.spring.techpractica.Core.System.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface SystemRepository extends JpaRepository<System, UUID> {
    boolean existsByName(String name);

    List<System> findAllByNameIn(Collection<String> names);
}
