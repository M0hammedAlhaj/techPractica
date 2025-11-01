package com.spring.techpractica.infrastructure.jwt.validation;

import com.spring.techpractica.infrastructure.cor.AbstractHandler;
import com.spring.techpractica.infrastructure.jwt.exception.JwtValidationException;
import com.spring.techpractica.infrastructure.jwt.JwtExtracting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtExpireDateValidator extends AbstractHandler<String> {
    private final JwtExtracting jwtExtracting;

    @Override
    public void process(String token) {
        Date expiration = jwtExtracting.extractExpireDate(token);

        if (expiration.before(new Date())) {
            throw new JwtValidationException("Token has expired.");
        }
    }
}
