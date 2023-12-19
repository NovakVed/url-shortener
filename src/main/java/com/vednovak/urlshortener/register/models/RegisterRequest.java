package com.vednovak.urlshortener.register.models;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RegisterRequest {
    // TODO: add @Pattern() validation
    private String url;
    // TODO: add messages!
    private int redirectType = HttpStatus.FOUND.value();
}
