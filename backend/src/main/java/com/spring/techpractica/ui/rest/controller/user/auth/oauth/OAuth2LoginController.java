package com.spring.techpractica.ui.rest.controller.user.auth.oauth;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import com.spring.techpractica.core.user.UserAuthentication;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class OAuth2LoginController {

    @Operation(
            summary = "Login with GitHub",
            description = "Redirects the user to GitHub OAuth2 authorization page to authenticate using GitHub account"
    )
    @GetMapping("/github")
    public String loginWithGithub(@RequestParam(value = "mode", required = false) String mode,
                                  Authentication authentication,
                                  HttpSession session) {
        boolean linkModeRequested = "link".equalsIgnoreCase(mode);
        boolean hasAuthenticatedUser =
                authentication != null && authentication.isAuthenticated()
                        && authentication.getPrincipal() instanceof UserAuthentication;

        if (linkModeRequested && hasAuthenticatedUser) {
            UserAuthentication userAuthentication = (UserAuthentication) authentication.getPrincipal();
            session.setAttribute("oauth_user_id", userAuthentication.getUserId());
            session.setAttribute("oauth_mode", "link");
            return "redirect:/oauth2/authorization/github?mode=link";
        }

        session.setAttribute("oauth_mode", "login");
        session.removeAttribute("oauth_user_id");
        return "redirect:/oauth2/authorization/github?mode=login";
    }
}