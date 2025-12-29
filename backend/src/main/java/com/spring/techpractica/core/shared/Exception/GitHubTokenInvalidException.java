package com.spring.techpractica.core.shared.Exception;

public class GitHubTokenInvalidException extends RuntimeException {

    public GitHubTokenInvalidException() {
        super("GitHub access token is invalid or expired. User must re-authenticate.");
    }
}