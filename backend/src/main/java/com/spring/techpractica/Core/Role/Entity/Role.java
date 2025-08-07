package com.spring.techpractica.Core.Role.Entity;

import com.spring.techpractica.Core.Shared.BaseEntity;
import com.spring.techpractica.Core.Role.Model.RoleType;
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
