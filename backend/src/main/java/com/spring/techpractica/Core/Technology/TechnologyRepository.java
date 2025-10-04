package com.spring.techpractica.Core.Technology;

import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Shared.BaseRepository;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface TechnologyRepository extends BaseRepository<Technology> {
    boolean existsByName(String name);

    List<Technology> findAllByNames(Collection<String> names);

    Optional<Technology> findTechnologyByName(String technology);

    List<Technology> findAll();

    List<Technology> findAllByIds(Set<UUID> ids);

    List<Technology> findAllByFieldId(UUID fieldId);

    void deleteById(UUID id);
}

