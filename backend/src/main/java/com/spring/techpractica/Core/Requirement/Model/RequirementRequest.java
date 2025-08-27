package com.spring.techpractica.Core.Requirement.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RequirementRequest {

    private String fieldName;
    private List<String> technologies;
}