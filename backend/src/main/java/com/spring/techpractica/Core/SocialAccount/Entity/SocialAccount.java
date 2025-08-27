package com.spring.techpractica.Core.SocialAccount.Entity;

import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.SocialAccount.model.PlatformName;
import com.spring.techpractica.Core.SocialAccount.model.SocialAccountId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SOCIAL_ACCOUNTS")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SocialAccount {

    @EmbeddedId
    private SocialAccountId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "profile_url")
    private String profileUrl;
}
