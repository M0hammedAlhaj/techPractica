package com.spring.techpractica.ui.rest.resources.social.account;

import com.spring.techpractica.core.social.account.entity.SocialAccount;
import com.spring.techpractica.core.social.account.model.PlatformName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialAccountResource {
    private final PlatformName platform;

    private final String profileUrl;

    public SocialAccountResource(SocialAccount socialAccount) {
        if (socialAccount == null) {
        throw new IllegalArgumentException("socialAccount is null");
        }
        this.platform = socialAccount.getId().getPlatformName();

        this.profileUrl = socialAccount.getProfileUrl();

    }
}
