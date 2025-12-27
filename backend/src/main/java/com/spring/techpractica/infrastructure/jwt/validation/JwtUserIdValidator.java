package com.spring.techpractica.infrastructure.jwt.validation;

import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import com.spring.techpractica.infrastructure.cor.AbstractHandler;
import com.spring.techpractica.infrastructure.jwt.exception.JwtValidationException;
import com.spring.techpractica.infrastructure.jwt.JwtExtracting;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class JwtUserIdValidator extends AbstractHandler<String> {

    private final JwtExtracting jwtExtracting;
    private final UserRepository userRepository;

    @Override
    public void process(String token) {
        UUID actualId = jwtExtracting.extractId(token);

        Optional<User> user = userRepository.findById(actualId);

        if (user.isEmpty()) {
            throw new JwtValidationException("User ID in token does not match the database");
        }
    }
}
