package com.spring.techpractica.UI.Rest.Controller.Admin.Technology.GetTechnology.GetTechnologiesByName;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record GetTechnologiesByNameRequest(@NotEmpty List< @NotBlank String> names) {
}
