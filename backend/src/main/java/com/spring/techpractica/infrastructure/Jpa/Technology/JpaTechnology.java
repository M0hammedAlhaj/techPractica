package com.spring.techpractica.infrastructure.Jpa.Technology;

import com.spring.techpractica.Core.Technology.Entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaTechnology extends JpaRepository<Technology, UUID> {
    boolean existsByName(String name);

    List<Technology> findAllByNameIn(Collection<String> names);

    Optional<Technology> findTechnologyByName(String technology);
}
