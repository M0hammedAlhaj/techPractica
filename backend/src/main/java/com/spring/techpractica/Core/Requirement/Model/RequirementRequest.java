package com.spring.techpractica.Core.Requirement.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class RequirementRequest {

    private UUID fieldId;
    private @UniqueElements List<UUID> technologies;
}