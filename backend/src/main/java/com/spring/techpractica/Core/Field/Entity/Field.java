package com.spring.techpractica.Core.Field.Entity;

import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import com.spring.techpractica.Core.Shared.BaseEntity;
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