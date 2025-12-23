package com.spring.techpractica.application.session.create.github.token;

public interface GitHubTokenValidator {
    boolean isValid(String accessToken);
}
