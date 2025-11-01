package com.spring.techpractica.ui.rest.controller.admin.system.update;

import jakarta.validation.constraints.NotBlank;

public record UpdateSystemRequest(@NotBlank String name) {
}
