package com.spring.techpractica.UI.Rest.Resources.Technology;

import com.fasterxml.jackson.annotation.JsonValue;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TechnologySummaryCollection {
    @JsonValue
    private final List<TechnologySummary> skills;

    public TechnologySummaryCollection(List<Technology> skills) {
        this.skills = skills.stream().map(TechnologySummary::new).collect(Collectors.toList());
    }
}
