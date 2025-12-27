package com.spring.techpractica.core.requirement.entity;

import com.spring.techpractica.core.field.entity.Field;
import com.spring.techpractica.core.requirement.technology.Entity.RequirementTechnology;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "REQUIREMENT")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Requirement extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "field_id", referencedColumnName = "id")
    private Field field;

    @OneToMany(mappedBy = "requirement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequirementTechnology> requirementTechnologies = new ArrayList<>();

    public void addRequirementTechnology(RequirementTechnology requirementTechnology) {
        if (requirementTechnologies == null) {
            requirementTechnologies = new ArrayList<>();
        }
        requirementTechnologies.add(requirementTechnology);
        requirementTechnology.setRequirement(this);
    }

    public void clearRequirementTechnologies() {
        requirementTechnologies.forEach(rt -> rt.setRequirement(null));
        requirementTechnologies.clear();
    }
}
