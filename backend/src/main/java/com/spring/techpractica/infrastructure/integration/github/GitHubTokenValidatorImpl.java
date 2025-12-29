package com.spring.techpractica.infrastructure.integration.github;

import com.spring.techpractica.application.session.create.github.token.GitHubTokenValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@AllArgsConstructor
public class GitHubTokenValidatorImpl implements GitHubTokenValidator {

    private final RestClient restClient;

    @Override
    public boolean isValid(String accessToken) {
        try {
            restClient.get()
                    .uri("https://api.github.com/user")
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Accept", "application/vnd.github+json")
                    .retrieve()
                    .toBodilessEntity();

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
