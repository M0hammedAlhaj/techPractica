package com.spring.techpractica.application.session.get.by.system;

import java.util.UUID;

public record GetSessionsBySystemCommand(UUID systemId,
                                         int size,
                                         int page) {
}
