package com.spring.techpractica.repository;

import com.spring.techpractica.model.entity.Session;
import com.spring.techpractica.model.entity.techSkills.System;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {


    Page<Session> findAllBySessionSystems(System system, Pageable pageable);

    Session findSessionBySessionId(long sessionId);


    long countBySessionSystems(List<System> sessionCategories);
}
