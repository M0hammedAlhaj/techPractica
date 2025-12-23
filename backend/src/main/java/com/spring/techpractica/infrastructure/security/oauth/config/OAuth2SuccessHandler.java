package com.spring.techpractica.infrastructure.security.oauth.config;

import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.user.Provider;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import com.spring.techpractica.infrastructure.jwt.JwtGeneration;
import com.spring.techpractica.infrastructure.security.oauth.GitHubEmailFetcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtGeneration jwtGeneration;
    private final UserRepository userRepository;


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        OAuth2AuthenticationToken token =
                (OAuth2AuthenticationToken) authentication;

        Provider provider = Provider.GITHUB;

        String providerId =
                token.getPrincipal().getAttribute("id").toString();

        User user = userRepository
                .findByProviderAndProviderId(provider, providerId)
                .orElseThrow(() ->
                        new ResourcesNotFoundException("User not found after OAuth2 login")
                );

        String jwt = jwtGeneration.generateLoginToken(
                user.getId(), user.getEmail(), user.getRoles()
        );

        System.out.println("jwt: " + jwt);

        Cookie cookie = new Cookie("access_token", jwt);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);

        response.addCookie(cookie);

        response.sendRedirect("http://localhost:3000/");
    }
}