package com.spring.techpractica.core.role;

import com.spring.techpractica.core.role.entity.Role;
import com.spring.techpractica.core.role.model.RoleType;
import com.spring.techpractica.core.shared.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends BaseRepository<Role> {
    Optional<Role> findByRoleType(RoleType roleType);

    List<Role> findAll();

    List<Role> findAllByIds(List<UUID> roleIds);
}
