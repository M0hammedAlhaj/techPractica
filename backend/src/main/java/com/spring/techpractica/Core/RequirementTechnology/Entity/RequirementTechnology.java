package com.spring.techpractica.Core.RequirementTechnology.Entity;

import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import com.spring.techpractica.Core.RequirementTechnology.Model.RequirementTechnologyId;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Requirement_Technology")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequirementTechnology {

    @EmbeddedId
    private RequirementTechnologyId id;

    @ManyToOne
    @MapsId("requirementId")
    @JoinColumn(name = "requirement_id")
    private Requirement requirement;

    @ManyToOne
    @MapsId("technologyId")
    @JoinColumn(name = "technology_id")
    private Technology technology;
}
