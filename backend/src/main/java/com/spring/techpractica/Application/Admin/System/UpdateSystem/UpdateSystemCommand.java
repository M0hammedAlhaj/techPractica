package com.spring.techpractica.Application.Admin.System.UpdateSystem;

import java.util.UUID;

public record UpdateSystemCommand(UUID systemId, String name) {
}