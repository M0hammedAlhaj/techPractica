package com.spring.techpractica.core.session.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;

@Service
@NoArgsConstructor
public final class SessionCodeGenerator {

    private static final String CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +   // A-Z
                    "abcdefghijklmnopqrstuvwxyz" +   // a-z
                    "0123456789" +                   // 0-9
                    "-_.~";

    private static final int CODE_LENGTH = 16;

    private static final SecureRandom RANDOM;

    static {
        RANDOM = new SecureRandom(
                String.valueOf(Instant.now().toEpochMilli()).getBytes()
        );
    }

    public static String generate() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }

        return code.toString();
    }
}
