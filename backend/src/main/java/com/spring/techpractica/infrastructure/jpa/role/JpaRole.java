package com.spring.techpractica.infrastructure.jpa.role;

import com.spring.techpractica.core.role.entity.Role;
import com.spring.techpractica.core.role.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaRole extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleType(RoleType roleType);
}