package com.spring.techpractica.UI.Rest.Controller.User.Auth.ActiveAccount;

import com.spring.techpractica.Application.User.AcvtiveAccount.ActiveAccountCommand;
import com.spring.techpractica.Application.User.AcvtiveAccount.ActiveAccountUseCase;
import com.spring.techpractica.infrastructure.Jwt.JwtExtracting;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class ActiveAccountController {
    private final JwtExtracting jwtExtracting;
    private final ActiveAccountUseCase useCase;

    @PostMapping("/api/v1/auth/active-account")
    public void verifyToken(@RequestParam String token,
                            HttpServletResponse response) throws IOException {
        UUID id = jwtExtracting.extractId(token);
        useCase.execute(new ActiveAccountCommand(id));
        try {
            response.sendRedirect("http://localhost:3000/User");
        } catch (Exception e) {
            response.sendRedirect("http//localhost:3030/not-found");
        }
    }
}
