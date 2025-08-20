package com.spring.techpractica.UI.Rest.Controller.Admin.System.GetSystem.GetSystemsByName;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record GetSystemsByNameRequest(@NotEmpty List<@NotBlank String> names) {
}
