package com.spring.techpractica.controller;

import com.spring.techpractica.dto.UserCreateAccount;
import com.spring.techpractica.dto.UserLogin;
import com.spring.techpractica.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authenticated")
public class AuthenticatedController {

    private final UserService userService;

    public AuthenticatedController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public void createAccount(@RequestBody UserCreateAccount userCreateAccount) {

       userService.createAccount(userCreateAccount);
    }

    @PostMapping("/login")
    public void login(@RequestBody UserLogin userLogin) {
        userService.userLogin(userLogin);
    }
}
