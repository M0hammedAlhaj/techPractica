package com.spring.techpractica.infrastructure.jpa.role;

import com.spring.techpractica.core.role.entity.Role;
import com.spring.techpractica.core.role.model.RoleType;
import com.spring.techpractica.core.role.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaRoleRepository implements RoleRepository {

    private final JpaRole jpaRole;

    @Override
    public Role save(Role role) {
        return null;
    }

    @Override
    public Role update(Role role) {
        return null;
    }

    @Override
    public Optional<Role> findById(UUID id) {
        return Optional.empty();
    }


    @Override
    public Optional<Role> findByRoleType(RoleType roleType) {
        return jpaRole.findByRoleType(roleType);
    }

    @Override
    public List<Role> findAll() {
        return jpaRole.findAll();
    }

    @Override
    public List<Role> findAllByIds(List<UUID> roleIds) {
        return jpaRole.findAllById(roleIds);
    }
}