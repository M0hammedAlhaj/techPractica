package com.spring.techpractica.ui.rest.controller.admin.technology.update;

import jakarta.validation.constraints.NotBlank;

import java.util.List;


public record UpdateTechnologyRequest( @NotBlank String name, List<String> fieldNames) {
}
