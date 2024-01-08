package com.vednovak.urlshortener.account.services.impl;

import com.vednovak.urlshortener.account.exceptions.AccountRegisterException;
import com.vednovak.urlshortener.account.models.Account;
import com.vednovak.urlshortener.account.models.AccountRequest;
import com.vednovak.urlshortener.account.models.AccountResponse;
import com.vednovak.urlshortener.account.repositories.AccountRepository;
import com.vednovak.urlshortener.account.services.AccountService;
import com.vednovak.urlshortener.message.services.MessageService;
import org.apache.commons.lang3.BooleanUtils;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.vednovak.urlshortener.account.utils.AccountConstants.*;

@Service
public class DefaultAccountService implements AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultAccountService.class);

    private final MessageService messageService;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultAccountService(MessageService messageService, AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.messageService = messageService;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AccountResponse register(final AccountRequest accountRequest) throws AccountRegisterException {
        if (doesAccountIdExists(accountRequest.getAccountId())) {
            // TODO: potential security vulnerability - user enumeration
            LOG.warn("Login for account: {} unsuccessful. Account already exists.", accountRequest.getAccountId());
            throw new AccountRegisterException(new AccountResponse(BooleanUtils.FALSE, messageService.getMessage(CREATE_ACCOUNT_UNSUCCESSFUL)));
        }

        final String generateRandomPassword = generateRandomPassword();
        saveAccount(accountRequest, generateRandomPassword);
        return new AccountResponse(BooleanUtils.TRUE, messageService.getMessage(CREATE_ACCOUNT_SUCCESSFUL), generateRandomPassword);
    }

    private boolean doesAccountIdExists(final String accountId) {
        return accountRepository.existsByAccountId(accountId);
    }

    private String generateRandomPassword() {
        final CharacterRule alphabeticRule = new CharacterRule(EnglishCharacterData.Alphabetical);
        final CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit);
        final PasswordGenerator passwordGenerator = new PasswordGenerator();
        return passwordGenerator.generatePassword(PASSWORD_LENGTH, alphabeticRule, digitRule);
    }

    private void saveAccount(final AccountRequest accountRequest, final String generateRandomPassword) {
        final Account user = new Account(accountRequest.getAccountId(), passwordEncoder.encode(generateRandomPassword));
        accountRepository.save(user);
    }
}
