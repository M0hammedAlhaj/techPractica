package com.spring.techpractica.infrastructure.security.oauth.config;

import com.spring.techpractica.application.user.auth.oauth.HandleOAuth2LoginUseCase;
import com.spring.techpractica.application.user.auth.oauth.OAuth2Command;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.infrastructure.security.oauth.GitHubEmailFetcher;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final GitHubEmailFetcher emailFetcher;
    private final HandleOAuth2LoginUseCase handleOAuth2LoginUseCase;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request)
            throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        Map<String, Object> attrs = oAuth2User.getAttributes();

        String name = (String) attrs.get("login");
        String email = (String) attrs.get("email");
        String githubToken = request.getAccessToken().getTokenValue();
        String providerId = attrs.get("id").toString();

        if (email == null) {
            email = emailFetcher.fetchPrimaryEmail(githubToken);
        }

        UUID linkingUserId = null;
        String linkingUserIdStr =
                (String) session.getAttribute("LINK_USER_ID");

        session.removeAttribute("LINK_USER_ID");

        if (linkingUserIdStr != null) {
            try {
                linkingUserId = UUID.fromString(linkingUserIdStr);
                session.removeAttribute("LINKING_USER_ID");
            } catch (IllegalArgumentException e) {
                session.removeAttribute("LINKING_USER_ID");
                throw new RuntimeException(e);
            }
        }

        OAuth2Command userInfo =
                new OAuth2Command(name, email, githubToken, providerId);

        handleOAuth2LoginUseCase.handle(userInfo, linkingUserId);

        return oAuth2User;
    }
}