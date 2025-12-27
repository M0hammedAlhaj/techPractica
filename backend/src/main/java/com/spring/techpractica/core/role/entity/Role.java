package com.spring.techpractica.core.role.entity;

import com.spring.techpractica.core.shared.BaseEntity;
import com.spring.techpractica.core.role.model.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "ROLES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseEntity implements GrantedAuthority {
    @Enumerated(EnumType.STRING)
    @Column(name = "role_type")
    private RoleType roleType;

    @Override
    public String getAuthority() {
        return roleType.toString();
    }
}