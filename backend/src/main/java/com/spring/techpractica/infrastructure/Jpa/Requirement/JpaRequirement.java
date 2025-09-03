package com.spring.techpractica.infrastructure.Jpa.Requirement;

import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaRequirement extends JpaRepository<Requirement, UUID> {
}
