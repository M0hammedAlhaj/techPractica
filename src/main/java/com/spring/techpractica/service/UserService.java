package com.spring.techpractica.service;

import com.spring.techpractica.dto.UserCreateAccount;
import com.spring.techpractica.dto.UserLogin;
import com.spring.techpractica.exception.AuthenticationException;
import com.spring.techpractica.model.entity.User;
import com.spring.techpractica.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //createAccount
    //HashingPassword
    //check email is exist
    //check username is exist
    //save User
    public void createAccount(UserCreateAccount userCreateAccount) {

        userRepository.findUserByUserEmail(userCreateAccount.getUserEmail())
                .ifPresent(user -> {
                    throw new AuthenticationException("Email is already in use");
                });

        userRepository.findUserByUserName(userCreateAccount.getName())
                .ifPresent(user -> {
                    throw new AuthenticationException("User name is already in use");
                });



        String encodedPassword = passwordEncoder.encode(userCreateAccount.getUserPassword());


        User user = User.builder()
                .userName(userCreateAccount.getName())
                .userFirstName(userCreateAccount.getFirstName())
                .userLastName(userCreateAccount.getLastName())
                .userEmail(userCreateAccount.getUserEmail())
                .userPassword(encodedPassword).build();

        userRepository.save(user);
    }

    //LOGIN
    public void userLogin(UserLogin userLogin) {

        User user = userRepository.findUserByUserEmail(userLogin.getUserEmail())
                .orElseThrow(() -> new AuthenticationException("User not found"));

        if (!passwordEncoder.matches(userLogin.getUserPassword(), user.getUserPassword())){
            throw new AuthenticationException("Wrong password");
        }
    }
}
