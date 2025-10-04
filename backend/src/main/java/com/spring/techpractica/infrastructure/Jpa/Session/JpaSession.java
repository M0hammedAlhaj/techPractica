package com.spring.techpractica.infrastructure.Jpa.Session;

import com.spring.techpractica.Core.Request.Entity.Request;
import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.Session.SessionStatus;
import com.spring.techpractica.Core.System.Entity.System;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface JpaSession extends JpaRepository<Session, UUID>, JpaSpecificationExecutor<Session> {


    Page<Session> findAllBySystemsAndStatusNotIn(
             List<System> systems,
            List<SessionStatus> excludedStatuses,
            Pageable pageable
    );

    Page<Session> findAllByStatusNotIn(List<SessionStatus> statuses, Pageable pageable);

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

    @Query("SELECT Request FROM Session s where s.id =:sessionId")
    List<Request> getRequestsBySession(@Param("sessionId") UUID sessionId);
}