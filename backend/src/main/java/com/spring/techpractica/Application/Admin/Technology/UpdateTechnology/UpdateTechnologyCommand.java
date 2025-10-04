package com.spring.techpractica.Application.Admin.Technology.UpdateTechnology;

import java.util.List;
import java.util.UUID;

public record UpdateTechnologyCommand(UUID technologyId, String name, List<String> fieldNames) {}

