package com.spring.techpractica.UI.Rest.Controller.User.Auth;

import com.spring.techpractica.Core.User.Exception.EmailAlreadyUsedException;
import com.spring.techpractica.Core.User.Exception.UserAuthenticationException;
import com.spring.techpractica.UI.Rest.Shared.StandardErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice(assignableTypes = UserAuthenticationController.class)
public class AuthenticationExceptionHandler {
    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<StandardErrorResponse> handleEmailAlreadyUsedException(EmailAlreadyUsedException ex) {
        StandardErrorResponse response = StandardErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .code("EMAIL_ALREADY_USED")
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<StandardErrorResponse> handleUserAuthenticationException(UserAuthenticationException ex) {
        StandardErrorResponse response = StandardErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .code("AUTH_FAILED")
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
