package com.spring.techpractica.infrastructure.Jpa.request;

import com.spring.techpractica.Core.Request.Entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaRequest extends JpaRepository<Request, UUID> {
    boolean existsRequestByUser_IdAndRequirement_Id(UUID userId, UUID requirementId);
}
