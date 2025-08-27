package com.spring.techpractica.UI.Rest.Resources.Technology;

import com.spring.techpractica.Core.Technology.Entity.Technology;
import com.spring.techpractica.UI.Rest.Resources.Field.FieldCollection;
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
