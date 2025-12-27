package com.spring.techpractica.ui.rest.resources.role;

import com.spring.techpractica.core.role.entity.Role;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RoleCollection {

    private final List<RoleResources> roleCollection;

    public RoleCollection(List<Role> roles) {
        this.roleCollection = roles.stream()
                .map(RoleResources::new)
                .collect(Collectors.toList());

    }
}
