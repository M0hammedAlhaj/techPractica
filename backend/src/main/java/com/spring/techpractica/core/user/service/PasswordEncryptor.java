package com.spring.techpractica.core.user.service;

public interface PasswordEncryptor {
    String hash(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
