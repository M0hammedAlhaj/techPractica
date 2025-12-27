package com.spring.techpractica.infrastructure.jpa.requirement;

import com.spring.techpractica.core.requirement.entity.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaRequirement extends JpaRepository<Requirement, UUID> {
    List<Requirement> findBySessionId(UUID sessionId);
}
