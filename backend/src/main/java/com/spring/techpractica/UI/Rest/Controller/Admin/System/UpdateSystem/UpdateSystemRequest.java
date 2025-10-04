package com.spring.techpractica.UI.Rest.Controller.Admin.System.UpdateSystem;

import jakarta.validation.constraints.NotBlank;

public record UpdateSystemRequest(@NotBlank String name) {
}
