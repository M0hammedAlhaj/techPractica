package com.spring.techpractica.repository;

import com.spring.techpractica.model.entity.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {
    Requirement getRequirementByCategoryCategoryNameAndSessionSessionId(String categoryName,Long sessionId);
}
