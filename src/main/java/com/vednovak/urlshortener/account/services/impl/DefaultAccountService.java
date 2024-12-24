package com.vednovak.urlshortener.account.services.impl;

import com.vednovak.urlshortener.account.exceptions.AccountRegisterException;
import com.vednovak.urlshortener.account.models.Account;
import com.vednovak.urlshortener.account.models.AccountRequest;
import com.vednovak.urlshortener.account.models.AccountResponse;
import com.vednovak.urlshortener.account.repositories.AccountRepository;
import com.vednovak.urlshortener.account.services.AccountService;
import com.vednovak.urlshortener.message.services.MessageService;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.vednovak.urlshortener.account.utils.AccountConstants.CREATE_ACCOUNT_SUCCESSFUL;
import static com.vednovak.urlshortener.account.utils.AccountConstants.CREATE_ACCOUNT_UNSUCCESSFUL;

@Service
public class DefaultAccountService implements AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultAccountService.class);

    private final MessageService messageService;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public DefaultAccountService(MessageService messageService,
                                 AccountRepository accountRepository,
                                 PasswordEncoder passwordEncoder) {
        this.messageService = messageService;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // TODO: do I need @Transactional here?
    @Override
    public AccountResponse create(final AccountRequest account) throws AccountRegisterException {
        if (doesAccountIdExists(account.getAccountId())) {
            LOG.warn("Login for account: {} unsuccessful. Account already exists.", account.getAccountId());
            final AccountResponse accountResponse = new AccountResponse(BooleanUtils.FALSE, messageService.getMessage(CREATE_ACCOUNT_UNSUCCESSFUL));
            throw new AccountRegisterException(accountResponse);
        }

        saveAccount(account);
        return new AccountResponse(BooleanUtils.TRUE, messageService.getMessage(CREATE_ACCOUNT_SUCCESSFUL));
    }

    private boolean doesAccountIdExists(final String accountId) {
        return accountRepository.existsByAccountId(accountId);
    }

    private void saveAccount(final AccountRequest accountRequest) {
        final Account user = new Account(accountRequest.getAccountId(), passwordEncoder.encode(accountRequest.getPassword()));
        accountRepository.save(user);
    }
}
