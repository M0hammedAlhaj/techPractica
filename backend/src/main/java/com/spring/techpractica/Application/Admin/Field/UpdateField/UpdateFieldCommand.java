package com.spring.techpractica.Application.Admin.Field.UpdateField;

import java.util.UUID;

public record UpdateFieldCommand(UUID fieldId, String name) {
}