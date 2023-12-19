package com.vednovak.urlshortener.register.controllers;

import com.vednovak.urlshortener.register.models.RegisterRequest;
import com.vednovak.urlshortener.register.models.RegisterResponse;
import com.vednovak.urlshortener.register.services.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Register", description = "the Register URL API")
@SecurityScheme(
        name = "Basic Auth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@RestController
@Validated
public class RegisterController {

    private static final Logger LOG = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private RegisterService registerService;

    @Operation(
            summary = "Shortens and Registers URL",
            description = "shortens the provided URL and registers it for the authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registered shortened url successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public RegisterResponse shortenAndRegisterUrl(
            @RequestBody @Valid RegisterRequest registerRequest,
            HttpServletRequest request
    ) throws UsernameNotFoundException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String accountId = authentication.getName();

        return registerService.shortenAndRegisterUrl(registerRequest, request, accountId);
    }
}
