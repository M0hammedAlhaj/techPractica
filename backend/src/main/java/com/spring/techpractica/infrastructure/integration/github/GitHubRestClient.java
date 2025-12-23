package com.spring.techpractica.infrastructure.integration.github;

import com.spring.techpractica.application.session.create.github.repo.GitHubRepositoryGateway;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
@AllArgsConstructor
public class GitHubRestClient implements GitHubRepositoryGateway {

    private final RestClient restClient;
    private static final String CREATE_REPO_URL = "https://api.github.com/user/repos";

    @Override
    public void createRepository(String accessToken, String repoName, boolean isPrivate) {
        restClient.post()
                .uri(CREATE_REPO_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "name", repoName,
                        "private", isPrivate,
                        "auto_init", true
                ))
                .retrieve()
                .toBodilessEntity();
    }
}