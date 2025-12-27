package com.spring.techpractica.application.admin.technology.create;

import java.util.List;

public record CreateTechnologyCommand(String name, List<String> fieldNames) {}

