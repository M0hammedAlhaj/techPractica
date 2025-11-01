package com.spring.techpractica.application.admin.system.get.by.name;

import java.util.List;

public record GetSystemsByNameCommand(List<String> systemsNames) {
}
