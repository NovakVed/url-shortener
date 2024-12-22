package com.vednovak.urlshortener.account.services;

import com.vednovak.urlshortener.account.exceptions.AccountRegisterException;
import com.vednovak.urlshortener.account.models.AccountRequest;
import com.vednovak.urlshortener.account.models.AccountResponse;

public interface AccountService {
    AccountResponse create(AccountRequest accountRequest) throws AccountRegisterException;
}
