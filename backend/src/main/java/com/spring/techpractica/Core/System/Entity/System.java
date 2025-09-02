package com.spring.techpractica.Core.System.Entity;

import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.Shared.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Table(name = "SYSTEMS")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class System extends BaseEntity {
    private String name;

    @ManyToMany(mappedBy = "systems", fetch = FetchType.LAZY)
    private List<Session> sessions;

    @Override
    public String toString() {
        return "System{" +
                "name='" + name + '\'' +
                '}';
    }
}
