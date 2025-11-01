package com.spring.techpractica.ui.rest.controller.admin.role.assign;

import java.util.List;
import java.util.UUID;

public record AssignRoleRequest(UUID id, List<UUID> roleIds) {
}
