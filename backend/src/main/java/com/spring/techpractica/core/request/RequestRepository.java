package com.spring.techpractica.core.request;

import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.shared.BaseRepository;

import java.util.Optional;
import java.util.UUID;

public interface RequestRepository extends BaseRepository<Request> {
    boolean existsByUserIdAndRequirementId(UUID id, UUID requirementId);

    Optional<Request> findByIdAndSessionId(UUID requestId, UUID sessionId);
}
