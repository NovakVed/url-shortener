package com.vednovak.urlshortener.register.services;

import com.vednovak.urlshortener.register.models.RegisterRequest;
import com.vednovak.urlshortener.register.models.RegisterResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface RegisterService {
    RegisterResponse shortenAndRegisterUrl(
            final RegisterRequest registerRequest,
            final HttpServletRequest request,
            final String accountId);
}
