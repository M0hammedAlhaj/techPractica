package com.spring.techpractica.Core.Technology.Entity;

import com.spring.techpractica.Core.Shared.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TECHNOLOGIES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Technology extends BaseEntity {
    private String name;
}
