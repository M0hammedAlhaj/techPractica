package com.spring.techpractica.Core.Technology;

import com.spring.techpractica.Core.Technology.Entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TechnologyRepository extends JpaRepository<Technology, UUID> {
    boolean existsByName(String name);

    List<Technology> findAllByNameIn(Collection<String> names);

    Optional<Technology> findTechnologyByName(String technology);


}
