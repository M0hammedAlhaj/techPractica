package com.spring.techpractica.core.user;

import com.spring.techpractica.core.role.entity.Role;
import com.spring.techpractica.core.role.model.RoleType;
import com.spring.techpractica.core.role.RoleRepository;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.user.service.PasswordEncryptor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserFactory {

    private final PasswordEncryptor passwordEncryptor;
    private final RoleRepository roleRepository;

    public User create(String name,
                       String email,
                       String password,
                       RoleType role) {
        String encryptedPassword = passwordEncryptor.hash(password);

        Role userRole = roleRepository.findByRoleType(role)
                .orElseThrow(() -> new ResourcesNotFoundException(role.toString()));

        return User.builder()
                .name(name)
                .email(email)
                .password(encryptedPassword)
                .roles(List.of(userRole))
                .build();
    }
}
