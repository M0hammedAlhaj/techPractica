package com.spring.techpractica.controller;

import com.spring.techpractica.exception.AuthenticationException;
import com.spring.techpractica.exception.ResourcesNotFoundException;
import com.spring.techpractica.exception.UserAlreadyExistsException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionHandlerController {


    @ExceptionHandler(JwtException.class)
    public ResponseEntity<HashMap<Object, Object>> jwtException(JwtException e) {
        HashMap<Object, Object> response = new HashMap<>();
        response.put("message", e.getMessage());
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<HashMap<Object, String>> authenticationException(AuthenticationException e) {

        HashMap<Object, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourcesNotFoundException.class)
    public ResponseEntity<HashMap<Object, String>> resourcesNotFoundException(ResourcesNotFoundException e) {
        HashMap<Object, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<HashMap<Object, String>> userAlreadyExistsException(ResourcesNotFoundException e) {
        HashMap<Object, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
