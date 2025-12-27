package com.spring.techpractica.ui.rest.resources.role;

import com.spring.techpractica.core.role.entity.Role;
import lombok.Getter;

import java.util.UUID;

@Getter
public class RoleResources {

    private final UUID id;
    private final String type;

    public RoleResources(Role role) {
        this.id = role.getId();
        this.type = role.getRoleType().toString();
    }
}