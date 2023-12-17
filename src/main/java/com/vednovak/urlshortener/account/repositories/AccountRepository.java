package com.vednovak.urlshortener.account.repositories;

import com.vednovak.urlshortener.account.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    boolean existsByAccountId(String accountId);
    Account findByAccountId(String accountId);
}
