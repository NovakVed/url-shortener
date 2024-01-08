package com.vednovak.urlshortener.account.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static com.vednovak.urlshortener.account.utils.AccountConstants.MAX_CHARACTER_LENGTH;
import static com.vednovak.urlshortener.account.utils.AccountConstants.MIN_CHARACTER_LENGTH;
import static com.vednovak.urlshortener.utils.RegexPatterns.ALPHABETIC_PATTERN;

@Data
public class AccountRequest {

    @NotBlank(message = "Account ID cannot be blank")
    @NotNull(message = "Account ID cannot be null")
    @Size(min = MIN_CHARACTER_LENGTH, max = MAX_CHARACTER_LENGTH, message = "Account ID length must be between 3 and 30 characters")
    @Pattern(regexp = ALPHABETIC_PATTERN, message = "Account ID must contain only alphabetical characters")
    private String accountId;
}
