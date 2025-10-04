package com.spring.techpractica.UI.Rest.Controller.Admin.Field.UpdateField;

import jakarta.validation.constraints.NotBlank;

public record UpdateFieldRequest(@NotBlank String name) {
}
