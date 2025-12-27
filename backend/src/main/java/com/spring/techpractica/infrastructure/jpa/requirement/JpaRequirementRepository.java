package com.spring.techpractica.infrastructure.jpa.requirement;

import com.spring.techpractica.core.requirement.entity.Requirement;
import com.spring.techpractica.core.requirement.RequirementRepository;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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


    @Override
    public List<Requirement> findBySessionId(UUID sessionId) {
        return jpaRequirement.findBySessionId(sessionId);
    }

    @Override
    public void delete(Requirement oldReq) {
        jpaRequirement.delete(oldReq);
    }
}
