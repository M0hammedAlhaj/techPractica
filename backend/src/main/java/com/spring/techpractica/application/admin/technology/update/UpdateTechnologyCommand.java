package com.spring.techpractica.application.admin.technology.update;

import java.util.List;
import java.util.UUID;

public record UpdateTechnologyCommand(UUID technologyId, String name, List<String> fieldNames) {}

