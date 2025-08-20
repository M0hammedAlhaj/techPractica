package com.spring.techpractica.Core.Technology.Entity;

import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Shared.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TECHNOLOGIES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Technology extends BaseEntity {
    private String name;

    @ManyToMany
    @JoinTable(
            name = "TECHNOLOGIES_FIELDS"
            , joinColumns = @JoinColumn(name = "technology_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "field_id", referencedColumnName = "id")
    )
    private List<Field> fields = new ArrayList<>();
}