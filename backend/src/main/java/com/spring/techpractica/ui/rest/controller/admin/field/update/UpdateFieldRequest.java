package com.spring.techpractica.ui.rest.controller.admin.field.update;

import jakarta.validation.constraints.NotBlank;

public record UpdateFieldRequest(@NotBlank String name) {
}
