package com.spring.techpractica.application.user.auth.register;

public record RegisterAccountCommand(String name, String email, String password) {
}