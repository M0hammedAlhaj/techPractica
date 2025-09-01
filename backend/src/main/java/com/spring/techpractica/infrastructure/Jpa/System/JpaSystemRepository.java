package com.spring.techpractica.infrastructure.Jpa.System;

import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.System.Entity.System;
import com.spring.techpractica.Core.System.SystemRepository;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import com.spring.techpractica.Core.Technology.TechnologyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaSystemRepository implements SystemRepository {
    private final JpaSystem jpaSystem;


    @Override
    public System save(System system) {
        return jpaSystem.save(system);
    }

    @Transactional
    @Override
    public System update(System system) {
        UUID id = system.getId();
        if (jpaSystem.existsById(id)) {
            return jpaSystem.save(system);
        }
        throw new ResourcesNotFoundException(id);
    }

    @Override
    public boolean existsByName(String name) {
        return jpaSystem.existsByName(name);
    }

    @Override
    public List<System> findAllByNames(Collection<String> names) {
        return jpaSystem.findAllByNameIn(names);
    }

    @Override
    public Optional<System> findSystemByName(String system) {
        return jpaSystem.findSystemByName(system);
    }

    @Override
    public List<System> findAll() {
        return jpaSystem.findAll();
    }

    @Override
    public Optional<System> findById(UUID id) {
        return jpaSystem.findById(id);
    }
}
