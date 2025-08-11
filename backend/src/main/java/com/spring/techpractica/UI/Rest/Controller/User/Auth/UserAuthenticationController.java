package com.spring.techpractica.UI.Rest.Controller.User.Auth;

import com.spring.techpractica.Application.User.RegisterAccount.RegisterAccountUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(name = "Authentication")
public class UserAuthenticationController {

    private final RegisterAccountUseCase registerAccountUseCase;

}

