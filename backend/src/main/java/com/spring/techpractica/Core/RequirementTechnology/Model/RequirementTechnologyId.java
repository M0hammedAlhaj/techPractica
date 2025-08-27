package com.spring.techpractica.Core.RequirementTechnology.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequirementTechnologyId {

    @Column(name = "requirement_id")
    private UUID requirementId;

    @Column(name = "technology_id")
    private UUID technologyId;
}
