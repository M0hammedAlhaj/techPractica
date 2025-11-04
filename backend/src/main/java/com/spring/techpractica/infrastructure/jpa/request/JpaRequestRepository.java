package com.spring.techpractica.infrastructure.jpa.request;

import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.request.RequestRepository;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaRequestRepository implements RequestRepository {

    private final JpaRequest jpaRequest;

    @Override
    public Request save(Request request) {
        return jpaRequest.save(request);
    }

    @Override
    public Request update(Request request) {
        if (jpaRequest.existsById(request.getId())) {
            return jpaRequest.save(request);
        }
        throw new ResourcesNotFoundException(request.getId());
    }

    @Override
    public Optional<Request> findById(UUID id) {
        return jpaRequest.findById(id);
    }

    @Override
    public boolean existsByUserIdAndRequirementId(UUID id, UUID requirementId) {
    return jpaRequest.existsRequestByUser_IdAndRequirement_Id(id, requirementId);
    }

    @Override
    public Optional<Request> findByIdAndSessionId(UUID requestId, UUID sessionId) {
        return jpaRequest.findByIdAndSessionId(requestId, sessionId);
    }

    @Override
    public boolean existsByRequirementId(UUID requirementId) {
        return jpaRequest.existsByRequirementId(requirementId);
    }

    @Override
    public void deleteAllByRequirementId(UUID id) {
        jpaRequest.deleteAllByRequirementId(id);
    }

}
