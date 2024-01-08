package com.vednovak.urlshortener.register.models;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import org.springframework.http.HttpStatus;

import static com.vednovak.urlshortener.register.utils.RegisterConstants.MAX_HTTP_STATUS_CODE;
import static com.vednovak.urlshortener.register.utils.RegisterConstants.MIN_HTTP_STATUS_CODE;

@Data
public class RegisterRequest {

    @URL
    private String url;

    @Range(min = MIN_HTTP_STATUS_CODE, max = MAX_HTTP_STATUS_CODE, message = "Redirect type must be 301 or 302")
    private int redirectType = HttpStatus.FOUND.value();
}
