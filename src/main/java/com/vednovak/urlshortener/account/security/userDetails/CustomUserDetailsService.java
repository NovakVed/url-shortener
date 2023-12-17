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

    // TODO: prettify this code when using optional, maybe some functional programming could do the trick :)
    // TODO: maybe lower memory complexity as currently I am using Optional<Account> might be a bit unnecessary?
    @Override
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
        Account account = accountRepository.findByAccountId(accountId);
        if (account == null) {
            throw new UsernameNotFoundException("User with accountId '" + accountId + "' not found");
        }

        return CustomUserDetails.fromAccount(account);
    }
}
