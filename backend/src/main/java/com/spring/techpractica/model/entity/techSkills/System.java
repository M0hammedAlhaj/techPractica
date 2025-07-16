package com.spring.techpractica.model.entity.techSkills;

import com.spring.techpractica.model.entity.Session;
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
public class System {

    @Id
    @Column(name = "system_name")
    private String systemName;

    @ManyToMany(mappedBy = "sessionSystems", fetch = FetchType.LAZY)
    private List<Session> sessions;

}
