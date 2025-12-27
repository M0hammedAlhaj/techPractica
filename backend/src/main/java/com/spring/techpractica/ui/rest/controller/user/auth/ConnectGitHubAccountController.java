package com.spring.techpractica.ui.rest.controller.user.auth;

import com.spring.techpractica.core.user.UserAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@Tag(name = "OAuth2 - GitHub", description = "Connect an existing account with GitHub")
public class ConnectGitHubAccountController {

    @Operation(
            summary = "Connect GitHub account",
            description = """
                       Starts GitHub OAuth2 flow to link GitHub with the currently authenticated user.
                       This endpoint requires the user to already be logged in.
                       """
    )
    @GetMapping("/github/connect")
    public void connect(@AuthenticationPrincipal UserAuthentication currentUser,
                        HttpSession session,
                        HttpServletResponse response) throws IOException {

        String userId = currentUser.getUser().getId().toString();

        String state = URLEncoder.encode(userId, StandardCharsets.UTF_8);

        response.sendRedirect("/oauth2/authorization/github?state=" + state);
    }
}
