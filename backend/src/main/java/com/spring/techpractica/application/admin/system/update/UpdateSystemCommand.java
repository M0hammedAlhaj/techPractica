package com.spring.techpractica.application.admin.system.update;

import java.util.UUID;

public record UpdateSystemCommand(UUID systemId, String name) {
}