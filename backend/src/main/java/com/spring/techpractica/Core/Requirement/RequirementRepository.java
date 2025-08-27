package com.spring.techpractica.Core.Requirement;

import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequirementRepository extends JpaRepository<Requirement, UUID> {
}
