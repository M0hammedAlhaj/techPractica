package com.spring.techpractica.UI.Rest.Controller.Admin.Field.CreateField;

import jakarta.validation.constraints.NotBlank;

public record CreateFieldRequest(@NotBlank String name) {
}
