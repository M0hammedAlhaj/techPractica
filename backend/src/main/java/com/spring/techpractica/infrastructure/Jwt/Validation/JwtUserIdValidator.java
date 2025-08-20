package com.spring.techpractica.infrastructure.Jwt.Validation;

import com.spring.techpractica.infrastructure.Cor.AbstractHandler;
import com.spring.techpractica.infrastructure.Jwt.Exception.JwtValidationException;
import com.spring.techpractica.infrastructure.Jwt.JwtExtracting;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class JwtUserIdValidator extends AbstractHandler<JwtValidationContext> {

    private final JwtExtracting jwtExtracting;

    @Override
    public void process(JwtValidationContext context) {
        UUID actualId = jwtExtracting.extractId(context.token());

        if (!actualId.equals(context.expectedUserId())) {
            throw new JwtValidationException("User ID in token does not match the request");
        }
    }
}
