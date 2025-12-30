package com.spring.techpractica.application.admin.manage.delete.user;

import com.spring.techpractica.core.session.members.Entity.SessionMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionMemberJpa extends JpaRepository<SessionMember, UUID> {
    void deleteByUserId(UUID id);
}
