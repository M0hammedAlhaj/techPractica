package com.spring.techpractica.Application.Admin.System.GetSystem.GetSystemsByName;

import java.util.List;

public record GetSystemsByNameCommand(List<String> systemsNames) {
}
