package com.spring.techpractica.Core.Requirement.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
public class RequirementRequest {

    private String fieldName;
    private Set<String> technologies;
}