package com.spring.techpractica.Core.Technology.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface TechnologyRepository extends JpaRepository<Technology, UUID> {
    boolean existsByName(String name);

    List<Technology> findAllByNameIn(Collection<String> names);
}
