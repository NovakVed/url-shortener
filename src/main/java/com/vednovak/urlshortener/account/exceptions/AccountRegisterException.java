package com.vednovak.urlshortener.account.exceptions;

import com.vednovak.urlshortener.account.models.AccountResponse;
import lombok.Getter;

@Getter
public class AccountRegisterException extends Exception {

    private final AccountResponse accountResponse;

    public AccountRegisterException(final AccountResponse accountResponse) {
        this.accountResponse = accountResponse;
    }
}
