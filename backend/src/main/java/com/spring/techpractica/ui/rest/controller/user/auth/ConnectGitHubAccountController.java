package com.spring.techpractica.ui.rest.controller.user.auth;

import com.spring.techpractica.core.user.UserAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
@Tag(name = "OAuth2 - GitHub", description = "Connect an existing account with GitHub")
public class ConnectGitHubAccountController {

    @Operation(
            summary = "Connect GitHub account",
            description = """
                       Starts GitHub OAuth2 flow to link GitHub with the currently authenticated user.
                       This endpoint requires the user to already be logged in.
                       """
    )
    @GetMapping("/auth/github/connect")
    public void connect(@AuthenticationPrincipal UserAuthentication currentUser,
                        HttpSession session,
                        HttpServletResponse response) throws IOException {

        session.setAttribute("LINK_USER_ID", currentUser.getUser().getId());

        response.sendRedirect("/oauth2/authorization/github");
    }
}
