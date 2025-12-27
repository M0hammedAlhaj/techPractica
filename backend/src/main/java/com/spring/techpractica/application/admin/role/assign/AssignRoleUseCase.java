package com.spring.techpractica.application.admin.role.assign;

import com.spring.techpractica.core.role.entity.Role;
import com.spring.techpractica.core.role.RoleRepository;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AssignRoleUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public String execute(AssignRoleCommand command){
        User user = userRepository.getOrThrowByID(command.id());

        List<UUID> roleIds = command.roleIds();

        List<Role> roles = roleRepository.findAllByIds(roleIds);

        if (roles.size() != roleIds.size()) {
            List<UUID> foundIds = roles.stream()
                    .map(Role::getId)
                    .toList();

            List<UUID> missingIds = roleIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw new ResourcesNotFoundException("Roles not found for IDs: " + missingIds);
        }

        user.updateRoles(roles);

        userRepository.update(user);

        return "Role Assigned";
    }
}