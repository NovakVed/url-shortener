package com.vednovak.urlshortener.account.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponse {

    private String success;
    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    public AccountResponse(String success, String description) {
        this.success = success;
        this.description = description;
    }
}
