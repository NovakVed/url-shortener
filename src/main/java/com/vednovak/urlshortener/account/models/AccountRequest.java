package com.vednovak.urlshortener.account.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static com.vednovak.urlshortener.account.utils.AccountConstants.*;
import static com.vednovak.urlshortener.utils.RegexPatterns.ALPHABETIC_PATTERN;
import static com.vednovak.urlshortener.utils.RegexPatterns.PASSWORD_PATTERN;

@Data
public class AccountRequest {

    @NotBlank(message = "Account ID cannot be blank")
    @NotNull(message = "Account ID cannot be null")
    @Size(min = MIN_ACCOUNT_CHARACTER_LENGTH, max = MAX_CHARACTER_LENGTH, message = "Account ID length must be between 3 and 30 characters")
    @Pattern(regexp = ALPHABETIC_PATTERN, message = "Account ID must contain only alphabetical characters")
    private String accountId;

    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be null")
    @Size(min = MIN_PASSWORD_LENGTH, max = MAX_CHARACTER_LENGTH, message = "Password length must be between 8 and 30 characters")
    @Pattern(regexp = PASSWORD_PATTERN, message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;
}
