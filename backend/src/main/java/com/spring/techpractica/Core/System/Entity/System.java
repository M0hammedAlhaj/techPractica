package com.spring.techpractica.Core.System.Entity;

import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.Shared.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "SYSTEMS")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class System extends BaseEntity {
    private String name;
    
    @ManyToMany(mappedBy = "sessionSystems", fetch = FetchType.LAZY)
    private List<Session> sessions;
}
