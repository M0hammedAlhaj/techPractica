package com.spring.techpractica.core.social.account;

import com.spring.techpractica.core.social.account.entity.SocialAccount;
import com.spring.techpractica.core.social.account.model.SocialAccountId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, SocialAccountId> {

}
