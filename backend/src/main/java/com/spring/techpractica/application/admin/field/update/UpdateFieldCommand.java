package com.spring.techpractica.application.admin.field.update;

import java.util.UUID;

public record UpdateFieldCommand(UUID fieldId, String name) {
}