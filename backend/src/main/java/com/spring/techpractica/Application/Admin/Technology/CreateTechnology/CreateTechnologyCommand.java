package com.spring.techpractica.Application.Admin.Technology.CreateTechnology;

import java.util.List;
import java.util.UUID;

public record CreateTechnologyCommand(String name, List<String> fieldNames) {}

