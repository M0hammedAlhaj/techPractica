package com.spring.techpractica.Core.SocialAccount.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SocialAccountId implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "platform_name")
    private PlatformName platformName;

    @Column(name = "user_id")
    private UUID userId;
}
