package com.spring.techpractica.ui.rest.controller.admin.system.create;

import jakarta.validation.constraints.NotBlank;

public record CreateSystemRequest(@NotBlank String name) {
}
