package com.spring.techpractica.ui.rest.controller.user.auth;

import com.spring.techpractica.infrastructure.jwt.JwtExtracting;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@Tag(name = "OAuth2 - GitHub", description = "Connect an existing account with GitHub")
public class ConnectGitHubAccountController {

    private final JwtExtracting jwtExtracting;

    public ConnectGitHubAccountController(JwtExtracting jwtExtracting) {
        this.jwtExtracting = jwtExtracting;
    }

    @Operation(
            summary = "Connect GitHub account",
            description = """
                    Starts GitHub OAuth2 flow to link GitHub with the currently authenticated user.
                    This endpoint requires the user to already be logged in.
                    """
    )
    @GetMapping("/github/connect")
    public void connect(@RequestParam String token,
                        HttpServletRequest request,
                        HttpServletResponse response) throws IOException {

        String userId = String.valueOf(jwtExtracting.extractId(token));
        request.getSession().setAttribute("LINKING_USER_ID", userId.toString());

        response.sendRedirect("/oauth2/authorization/github");
    }


//    @GetMapping("/github/connect")
//    public void connect(@RequestParam("token") String token,
//                        HttpServletResponse response) throws IOException {
//
//        String userId = token;
//
//        String state = URLEncoder.encode(userId, StandardCharsets.UTF_8);
//
//        response.sendRedirect("/oauth2/authorization/github?state=" + state);
//    }

}
