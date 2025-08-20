package com.spring.techpractica.UI.Rest.Controller.Admin.System.CreateSystem;

import jakarta.validation.constraints.NotBlank;

public record CreateSystemRequest(@NotBlank String name) {
}
