package com.spring.techpractica.ui.rest.controller.user.profile.request;

import com.spring.techpractica.core.social.account.model.PlatformName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SocialAccountRequest {

    @EqualsAndHashCode.Include
    @NotNull
    private PlatformName platformName;

    @NotBlank
    private String profileUrl;
}
