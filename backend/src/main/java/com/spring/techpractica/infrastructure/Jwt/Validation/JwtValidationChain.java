package com.spring.techpractica.infrastructure.Jwt.Validation;

import com.spring.techpractica.infrastructure.Cor.Handler;
import org.springframework.stereotype.Component;

@Component
public class JwtValidationChain {
    private final JwtExpireDateValidator expireDateValidator;
    private final JwtUserIdValidator userIdValidator;

    private Handler<String> chain;

    public JwtValidationChain(JwtExpireDateValidator expireDateValidator,
                              JwtUserIdValidator userIdValidator) {
        this.expireDateValidator = expireDateValidator;
        this.userIdValidator = userIdValidator;
        initChain();
    }

    private void initChain() {
        expireDateValidator.setNext(userIdValidator);
        chain = expireDateValidator;
    }

    public void validate(String token) {
        chain.handle(token);
    }
}
