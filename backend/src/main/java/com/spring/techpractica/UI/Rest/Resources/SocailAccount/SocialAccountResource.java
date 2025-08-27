package com.spring.techpractica.UI.Rest.Resources.SocailAccount;

import com.spring.techpractica.Core.SocialAccount.Entity.SocialAccount;
import com.spring.techpractica.Core.SocialAccount.model.PlatformName;
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
