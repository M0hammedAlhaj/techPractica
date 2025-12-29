package com.spring.techpractica.core.session.service;

import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor
public final class SessionCodePolicy {

    // 16 chars: upper, lower, digits, symbols
    private static final String SESSION_CODE_REGEX =
            "^[A-Za-z0-9\\-_.~]{16}$";

    private static final Pattern PATTERN =
            Pattern.compile(SESSION_CODE_REGEX);


    public static boolean isPrivateSessionCode(String code) {
        return code != null
                && !code.isBlank()
                && PATTERN.matcher(code).matches();
    }
}