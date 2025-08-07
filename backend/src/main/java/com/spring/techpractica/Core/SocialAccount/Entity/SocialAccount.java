package com.spring.techpractica.Core.SocialAccount.Entity;

import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.SocialAccount.model.PlatformName;
import com.spring.techpractica.Core.SocialAccount.model.SocialAccountId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "SOCIAL_ACCOUNTS")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@IdClass(SocialAccountId.class)
public class SocialAccount {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "platform_name")
    private PlatformName platformName;

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "profile_url")
    private String profileUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}