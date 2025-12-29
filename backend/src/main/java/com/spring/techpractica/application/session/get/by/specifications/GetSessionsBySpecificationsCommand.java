package com.spring.techpractica.application.session.get.by.specifications;

public record GetSessionsBySpecificationsCommand(String sort,
                                                 String fieldName,
                                                 String sessionName,
                                                 String sessionCode,
                                                 String systemName,
                                                 int page,
                                                 int size) {
}
