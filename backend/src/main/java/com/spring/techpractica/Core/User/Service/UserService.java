package com.spring.techpractica.Core.User.Service;

import com.spring.techpractica.Core.User.User;
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
