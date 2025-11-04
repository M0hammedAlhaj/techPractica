package com.spring.techpractica.infrastructure.jpa.request;

import com.spring.techpractica.core.request.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JpaRequest extends JpaRepository<Request, UUID> {

    boolean existsRequestByUser_IdAndRequirement_Id(UUID userId, UUID requirementId);

    @Query("""
    SELECT r\s
    FROM Request r\s
    JOIN r.requirement req\s
    JOIN req.session s\s
    WHERE r.id = :requestId\s
    AND s.id = :sessionId
                           \s""")
    Optional<Request> findByIdAndSessionId(@Param("requestId") UUID requestId,
                                  @Param("sessionId") UUID sessionId);

    boolean existsByRequirementId(UUID requirementId);

    void deleteAllByRequirementId(UUID id);
}
