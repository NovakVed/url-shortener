package com.vednovak.urlshortener.account.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponse {

    private String success;
    private String description;
}
