package com.spring.techpractica.Core.Request;

import com.spring.techpractica.Core.Request.Entity.Request;
import com.spring.techpractica.Core.Shared.BaseRepository;

import java.util.UUID;

public interface RequestRepository extends BaseRepository<Request> {
    boolean existsByUserIdAndRequirementId(UUID id, UUID requirementId);
}
