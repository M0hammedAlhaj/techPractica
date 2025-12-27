package com.spring.techpractica.infrastructure.security.oauth.config;

import com.spring.techpractica.application.user.auth.oauth.HandleOAuth2LoginUseCase;
import com.spring.techpractica.application.user.auth.oauth.OAuth2Command;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.infrastructure.security.oauth.GitHubEmailFetcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final GitHubEmailFetcher emailFetcher;
    private final HandleOAuth2LoginUseCase handleOAuth2LoginUseCase;

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

        // 🔥 اقرأ state (اللي فيه userId)
        String state = request.getAdditionalParameters().get("state").toString();
        UUID linkingUserId = UUID.fromString(state);

        OAuth2Command userInfo =
                new OAuth2Command(name, email, githubToken, providerId);

        handleOAuth2LoginUseCase.handle(userInfo, linkingUserId);

        return oAuth2User;
    }
}
