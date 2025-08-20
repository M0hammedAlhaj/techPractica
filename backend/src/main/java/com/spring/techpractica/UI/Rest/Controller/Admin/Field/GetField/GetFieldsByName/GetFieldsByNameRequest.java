package com.spring.techpractica.UI.Rest.Controller.Admin.Field.GetField.GetFieldsByName;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record GetFieldsByNameRequest(@NotEmpty List<@NotBlank String> names) {
}
