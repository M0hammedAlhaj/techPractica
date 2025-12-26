package com.spring.techpractica.application.user.auth.oauth;

import java.util.UUID;

public record OAuth2Command(String name,
                            String email,
                            String githubToken,
                            String providerId,
                            String mode,
                            UUID sessionUserId) {

    public boolean isLinkMode() {
        return "link".equalsIgnoreCase(mode) && sessionUserId != null;
    }
}