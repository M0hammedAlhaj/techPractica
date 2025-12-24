package com.spring.techpractica.core.request;

import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.shared.BaseRepository;
import com.spring.techpractica.core.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RequestRepository extends BaseRepository<Request> {
    boolean existsByUserIdAndRequirementId(UUID id, UUID requirementId);

    Optional<Request> findByIdAndSessionId(UUID requestId, UUID sessionId);

    boolean existsByRequirementId(UUID requirementId);

    void deleteAllByRequirementId(UUID id);

    List<Request> findByUserAndRequirement_Session_Id(User user, UUID requirementSessionId);
}
