package com.spring.techpractica.infrastructure.jpa.session;

import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.SessionStatus;
import com.spring.techpractica.core.system.entity.System;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaSession extends JpaRepository<Session, UUID>, JpaSpecificationExecutor<Session> {
    Page<Session> findBySystemsInAndStatusNotInAndIsPrivateFalse(
            List<System> systems,
            List<SessionStatus> excludedStatuses,
            Pageable pageable
    );

    Page<Session> findAllByStatusNotInAndIsPrivateFalse(
            List<SessionStatus> statuses, Pageable pageable
    );

    @Query("SELECT s FROM Session s " +
            "JOIN s.members m " +
            "WHERE m.user.id = :userId " +
            "AND s.status NOT IN :statuses")
    Page<Session> findAllByUserIdAndStatusNotIn(@Param("userId") UUID userId,
                                                @Param("statuses") List<SessionStatus> statuses,
                                                Pageable pageable);


    @Query("SELECT COUNT(s) FROM Session s WHERE s.status NOT IN :statuses ")
    long getAllSessionsCount(@Param("statuses") List<SessionStatus> statuses);

    @Query("SELECT COUNT(s) FROM Session s JOIN s.members m WHERE s.status NOT IN :statuses  AND m.user.id = :userId ")
    long getAllSessionsCountByUser(@Param("statuses") List<SessionStatus> statuses,@Param("userId") UUID userId);

    @Query("""
    SELECT r
    FROM Request r
    JOIN r.requirement req
    JOIN req.session s
    WHERE s.id = :sessionId""")
    List<Request> getRequestsBySessionId(@Param("sessionId") UUID sessionId);

    @Query("SELECT s FROM Session s " +
            "JOIN s.requirements r " +
            "WHERE r.field.id = :fieldId " +
            "AND s.status NOT IN :statuses")
    List<Session> getSessionsByField_Id(@Param("fieldId") UUID fieldId,@Param("statuses") List<SessionStatus> statuses);

    @Query("SELECT s FROM Session s " +
            "JOIN s.systems sy " +
            "WHERE sy.id = :systemId " +
            "AND s.status NOT IN :statuses")
    List<Session> getSessionsBySystem_Id(@Param("systemId") UUID systemId,@Param("statuses") List<SessionStatus> statuses);

    @Query("SELECT s FROM Session s " +
            "JOIN s.requirements r " +
            "JOIN r.requirementTechnologies rt " +
            "WHERE rt.technology.id = :technologyId " +
            "AND (:statuses IS NULL OR s.status NOT IN :statuses)")
    List<Session> getSessionsByTechnology_Id(@Param("technologyId") UUID technologyId,@Param("statuses") List<SessionStatus> statuses);

}