package com.spring.techpractica.UI.Rest.Resources.System;

import com.spring.techpractica.Core.System.Entity.System;
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
