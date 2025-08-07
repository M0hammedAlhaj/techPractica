package com.spring.techpractica.Core.User.Service;

public interface PasswordEncryptor {
    String hash(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
