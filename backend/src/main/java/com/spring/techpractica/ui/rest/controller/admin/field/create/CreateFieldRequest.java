package com.spring.techpractica.ui.rest.controller.admin.field.create;

import jakarta.validation.constraints.NotBlank;

public record CreateFieldRequest(@NotBlank String name) {
}
