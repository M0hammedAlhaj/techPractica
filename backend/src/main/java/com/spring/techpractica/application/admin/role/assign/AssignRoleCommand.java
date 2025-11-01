package com.spring.techpractica.application.admin.role.assign;

import java.util.List;
import java.util.UUID;

public record AssignRoleCommand(UUID id, List<UUID> roleIds) {
}
