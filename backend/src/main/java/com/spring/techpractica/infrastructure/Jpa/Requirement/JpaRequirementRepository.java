package com.spring.techpractica.infrastructure.Jpa.Requirement;

import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import com.spring.techpractica.Core.Requirement.RequirementRepository;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaRequirementRepository implements RequirementRepository {

    private final JpaRequirement jpaRequirement;

    @Override
    public Requirement save(Requirement requirement) {
        return jpaRequirement.save(requirement);
    }

    @Override
    public Requirement update(Requirement requirement) {
        if (jpaRequirement.existsById(requirement.getId())) {
            return jpaRequirement.save(requirement);
        }
        throw new ResourcesNotFoundException(requirement.getId());
    }

    @Override
    public Optional<Requirement> findById(UUID id) {
        return jpaRequirement.findById(id);
    }
}
