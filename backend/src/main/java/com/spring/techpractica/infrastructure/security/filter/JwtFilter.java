package com.spring.techpractica.infrastructure.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.techpractica.core.user.UserAuthentication;
import com.spring.techpractica.core.user.UserRepository;
import com.spring.techpractica.ui.rest.shared.StandardErrorResponse;
import com.spring.techpractica.infrastructure.jwt.exception.JwtValidationException;
import com.spring.techpractica.infrastructure.jwt.JwtExtracting;
import com.spring.techpractica.infrastructure.jwt.validation.JwtValidationChain;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtExtracting jwtExtracting;
    private final JwtValidationChain jwtValidation;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String headerAuthorization = request.getHeader("Authorization");
            String token = null;
            UUID id = null;

            if (headerAuthorization != null && headerAuthorization.startsWith("Bearer ")) {
                token = headerAuthorization.substring(7);
                id = jwtExtracting.extractId(token);
            }

            if (token == null && request.getParameter("token") != null) {
                token = request.getParameter("token");
                id = jwtExtracting.extractId(token);
            }

            if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                jwtValidation.validate(token);

                UserAuthentication userDetails = userRepository.findById(id)
                        .map(UserAuthentication::new)
                        .get();

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);
        } catch (JwtValidationException | io.jsonwebtoken.ExpiredJwtException |
                 io.jsonwebtoken.security.SignatureException |
                 io.jsonwebtoken.MalformedJwtException ex) {
            StandardErrorResponse errorResponse = StandardErrorResponse.builder()
                    .message("Invalid or tempered Jwt token")
                    .code("JWT_INVALID")
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .build();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        }
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\":\"" + message + "\"}");
    }
}
