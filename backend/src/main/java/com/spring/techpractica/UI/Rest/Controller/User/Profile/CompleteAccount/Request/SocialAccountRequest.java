package com.spring.techpractica.UI.Rest.Controller.User.Profile.CompleteAccount.Request;

import com.spring.techpractica.Core.SocialAccount.model.PlatformName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class SocialAccountRequest {
    @NotBlank
    private String profileUrl;
    @NotNull
    private PlatformName platformName;
}
