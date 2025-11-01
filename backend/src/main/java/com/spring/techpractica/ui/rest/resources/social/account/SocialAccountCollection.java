package com.spring.techpractica.ui.rest.resources.social.account;

import com.fasterxml.jackson.annotation.JsonValue;
import com.spring.techpractica.core.social.account.entity.SocialAccount;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
public class SocialAccountCollection {
  @JsonValue
  private final  List<SocialAccountResource> socialAccounts;

  public SocialAccountCollection(List<SocialAccount> socialAccounts) {
        this.socialAccounts = socialAccounts.stream()
             .map(SocialAccountResource::new).collect(Collectors.toList());
    }
}
