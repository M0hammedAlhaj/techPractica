package com.spring.techpractica.infrastructure.Jpa.request;

import com.spring.techpractica.Core.Request.Entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaRequest extends JpaRepository<Request, UUID> {
    boolean existsRequestByUser_IdAndRequirement_Id(UUID userId, UUID requirementId);

}
