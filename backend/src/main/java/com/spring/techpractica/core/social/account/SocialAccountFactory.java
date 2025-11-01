package com.spring.techpractica.core.social.account;

import com.spring.techpractica.core.social.account.entity.SocialAccount;
import com.spring.techpractica.core.social.account.model.PlatformName;
import com.spring.techpractica.core.social.account.model.SocialAccountId;
import com.spring.techpractica.core.user.User;
import org.springframework.stereotype.Component;

@Component
public class SocialAccountFactory {

    public SocialAccount create(PlatformName platformName, String profileUrl, User user) {
        return SocialAccount.builder().id(SocialAccountId.builder()
                        .platformName(platformName)
                        .userId(user.getId())
                        .build())
                .profileUrl(profileUrl)
                .user(user)
                .build();
    }
}
