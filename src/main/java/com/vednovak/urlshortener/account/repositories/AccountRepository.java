package com.vednovak.urlshortener.account.repositories;

import com.vednovak.urlshortener.account.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    boolean existsByAccountId(String accountId);
    Optional<Account> findByAccountId(String accountId);
}
