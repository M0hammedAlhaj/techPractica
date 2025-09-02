package com.spring.techpractica.infrastructure.Jpa.Session;

import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.System.Entity.System;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaSession extends JpaRepository<Session, UUID> {
    List<Session> findAllBySystems(List<System> system, Pageable pageable);
}
