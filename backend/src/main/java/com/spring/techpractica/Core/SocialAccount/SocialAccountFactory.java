package com.spring.techpractica.Core.SocialAccount;

import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.SessionMembers.Entity.SessionMember;
import com.spring.techpractica.Core.SessionMembers.model.Role;
import com.spring.techpractica.Core.SessionMembers.model.UserSessionId;
import com.spring.techpractica.Core.SocialAccount.Entity.SocialAccount;
import com.spring.techpractica.Core.SocialAccount.model.PlatformName;
import com.spring.techpractica.Core.SocialAccount.model.SocialAccountId;
import com.spring.techpractica.Core.User.User;
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
