package com.spring.techpractica.ui.rest.resources.system;

import com.spring.techpractica.core.system.entity.System;
import lombok.*;

import java.util.UUID;
@NoArgsConstructor
@Getter
@Setter
public class SystemResources {
    private UUID id;
    private String name;

    public SystemResources(System system) {
        this.id = system.getId();
        this.name = system.getName();
    }
}
