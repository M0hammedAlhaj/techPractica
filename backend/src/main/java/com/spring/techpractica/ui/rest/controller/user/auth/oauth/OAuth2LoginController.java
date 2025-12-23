package com.spring.techpractica.ui.rest.controller.user.auth.oauth;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class OAuth2LoginController {

    @Operation(
            summary = "Login with GitHub",
            description = "Redirects the user to GitHub OAuth2 authorization page to authenticate using GitHub account"
    )
    @GetMapping("/github")
    public String loginWithGithub() {
        return "redirect:/oauth2/authorization/github";
    }
}