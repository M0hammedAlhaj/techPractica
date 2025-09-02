package com.spring.techpractica.Core.Technology;

import com.spring.techpractica.Core.Shared.BaseRepository;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TechnologyRepository extends BaseRepository<Technology> {
    boolean existsByName(String name);

    List<Technology> findAllByNames(Collection<String> names);

    Optional<Technology> findTechnologyByName(String technology);

    List<Technology> findAll();
}
