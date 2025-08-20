package com.spring.techpractica.Core.Field.Entity;

import com.spring.techpractica.Core.Shared.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CATEGORIES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Field extends BaseEntity {
    private String name;
}

