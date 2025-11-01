package com.spring.techpractica.ui.rest.controller.admin.technology.create;

import jakarta.validation.constraints.NotBlank;

import java.util.List;


public record CreateTechnologyRequest(@NotBlank String name, List<String> fieldNames) {
}
