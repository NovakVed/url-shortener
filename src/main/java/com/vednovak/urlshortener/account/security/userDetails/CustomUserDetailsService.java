package com.vednovak.urlshortener.account.security.userDetails;

import com.vednovak.urlshortener.account.models.Account;
import com.vednovak.urlshortener.account.repositories.AccountRepository;
import com.vednovak.urlshortener.message.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.vednovak.urlshortener.account.utils.AccountConstants.USER_AUTHENTICATION_UNSUCCESSFUL;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final AccountRepository accountRepository;
    private final MessageService messageService;

    @Autowired
    public CustomUserDetailsService(AccountRepository accountRepository, MessageService messageService) {
        this.accountRepository = accountRepository;
        this.messageService = messageService;
    }

    @Override
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
        final Account account = getAccountOrElseThrow(accountId);
        return CustomUserDetails.fromAccount(account);
    }

    private Account getAccountOrElseThrow(final String accountId) throws UsernameNotFoundException {
        return accountRepository
                .findByAccountId(accountId)
                .orElseThrow(() -> {
                    LOG.info("User with accountId '{}' not found", accountId);
                    return new UsernameNotFoundException(messageService.getMessage(USER_AUTHENTICATION_UNSUCCESSFUL));
                });
    }
}
