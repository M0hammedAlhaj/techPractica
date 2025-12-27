package com.spring.techpractica.ui.rest.resources.technology;

import com.spring.techpractica.core.technology.entity.Technology;
import lombok.Getter;

import java.util.List;

@Getter
public class TechnologyCollection {

    private final List<TechnologyResources> technologies;

    public TechnologyCollection(List<Technology> technologies) {
        this.technologies = technologies.stream().map(TechnologyResources::new)
                .toList();
    }
}
