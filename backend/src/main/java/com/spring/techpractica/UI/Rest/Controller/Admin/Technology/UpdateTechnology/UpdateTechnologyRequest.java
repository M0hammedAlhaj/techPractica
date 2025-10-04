package com.spring.techpractica.UI.Rest.Controller.Admin.Technology.UpdateTechnology;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;


public record UpdateTechnologyRequest( @NotBlank String name, List<String> fieldNames) {
}
