package com.vednovak.urlshortener.account.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

// TODO: load from constants / messages.properties
@Data
public class AccountRequest {
    @NotBlank(message = "Account ID cannot be blank")
    @NotNull(message = "Account ID cannot be null")
    @Size(min = 3, max = 30, message = "Account ID length must be between 3 and 30 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Account ID must contain only alphabetical characters")
    private String accountId;
}
