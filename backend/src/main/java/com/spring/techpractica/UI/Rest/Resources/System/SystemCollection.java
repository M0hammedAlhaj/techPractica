package com.spring.techpractica.UI.Rest.Resources.System;

import com.spring.techpractica.Core.System.Entity.System;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import com.spring.techpractica.UI.Rest.Resources.Field.FieldCollection;
import com.spring.techpractica.UI.Rest.Resources.Technology.TechnologyResources;
import lombok.Getter;

import java.util.List;

@Getter
public class SystemCollection {
    private final List<SystemResources> systems;

    public SystemCollection(List<System> systems) {
        this.systems = systems.stream().map(SystemResources::new).toList();
    }
}
