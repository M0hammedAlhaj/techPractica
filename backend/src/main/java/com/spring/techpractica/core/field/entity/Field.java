package com.spring.techpractica.core.field.entity;

import com.spring.techpractica.core.requirement.entity.Requirement;
import com.spring.techpractica.core.shared.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "FIELDS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Field extends BaseEntity {
    private String name;

    @OneToMany(mappedBy = "field")
    private List<Requirement> requirements;
}