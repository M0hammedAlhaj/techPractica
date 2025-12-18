package com.spring.techpractica.ui.rest.controller.admin.role.assign;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record AssignRoleRequest(@NotNull UUID id,@NotNull List<UUID> roleIds) {
}