package com.vednovak.urlshortener.account.security.userDetails;

import com.vednovak.urlshortener.account.models.Account;
import com.vednovak.urlshortener.account.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
        final Account account = accountRepository
                .findByAccountId(accountId)
                .orElseThrow(() -> new UsernameNotFoundException("User with accountId '" + accountId + "' not found"));
        return CustomUserDetails.fromAccount(account);
    }
}
