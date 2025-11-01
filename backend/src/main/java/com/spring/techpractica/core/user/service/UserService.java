package com.spring.techpractica.core.user.service;

import com.spring.techpractica.core.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserService {
    private final PasswordEncryptor passwordEncryptor;

    public void changePassword(User user, String newPassword) {
        String hashPassword = passwordEncryptor.hash(newPassword);
        user.setPassword(hashPassword);
    }
}
