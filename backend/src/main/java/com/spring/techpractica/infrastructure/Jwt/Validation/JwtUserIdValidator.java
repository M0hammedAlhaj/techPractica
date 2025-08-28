package com.spring.techpractica.infrastructure.Jwt.Validation;

import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserRepository;
import com.spring.techpractica.infrastructure.Cor.AbstractHandler;
import com.spring.techpractica.infrastructure.Jwt.Exception.JwtValidationException;
import com.spring.techpractica.infrastructure.Jwt.JwtExtracting;
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
