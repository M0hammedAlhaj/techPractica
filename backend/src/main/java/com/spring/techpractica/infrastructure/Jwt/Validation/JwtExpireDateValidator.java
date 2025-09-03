package com.spring.techpractica.infrastructure.Jwt.Validation;

import com.spring.techpractica.infrastructure.Cor.AbstractHandler;
import com.spring.techpractica.infrastructure.Jwt.Exception.JwtValidationException;
import com.spring.techpractica.infrastructure.Jwt.JwtExtracting;
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
