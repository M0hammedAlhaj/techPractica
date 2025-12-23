package com.spring.techpractica.application.user.auth.oauth;

public record OAuth2Command(String name, String email, String githubToken, String providerId) {
}