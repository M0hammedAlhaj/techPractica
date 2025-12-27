package com.spring.techpractica.application.admin.field.get.by.name;

import java.util.List;

public record GetFieldsByNameCommand(List<String> fieldsNames) {
}
