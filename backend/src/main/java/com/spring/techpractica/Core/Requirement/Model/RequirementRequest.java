package com.spring.techpractica.Core.Requirement.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class RequirementRequest {

    private UUID fieldName;
    private Set<UUID> technologies;
}