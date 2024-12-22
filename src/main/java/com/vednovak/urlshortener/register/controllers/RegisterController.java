package com.vednovak.urlshortener.register.controllers;

import com.vednovak.urlshortener.constants.SwaggerConstants;
import com.vednovak.urlshortener.register.models.RegisterRequest;
import com.vednovak.urlshortener.register.models.RegisterResponse;
import com.vednovak.urlshortener.register.services.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Register", description = "the Register URL API")
@SecurityScheme(
        name = "Basic Auth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@RestController
@RequestMapping(RegisterController.ENDPOINT)
@Validated
public class RegisterController {

    protected static final String ENDPOINT = "/api/v1/urls";

    private final RegisterService registerService;

    public RegisterController(final RegisterService registerService) {
        this.registerService = registerService;
    }

    @Operation(
            summary = "Shortens and Registers URL",
            description = "shortens the provided URL and registers it for the authenticated user",
            security = @SecurityRequirement(name = "Basic Auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerConstants.OK, description = "Registered shortened url successfully"),
            @ApiResponse(responseCode = SwaggerConstants.BAD_REQUEST, description = "Bad request - Invalid input data"),
            @ApiResponse(responseCode = SwaggerConstants.INTERNAL_SERVER_ERROR, description = "Internal server error")
    })
    @PostMapping
    public RegisterResponse shortenAndRegisterUrl(
            @RequestBody @Valid RegisterRequest registerRequest,
            HttpServletRequest request
    ) throws UsernameNotFoundException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String accountId = authentication.getName();

        return registerService.shortenAndRegisterUrl(registerRequest, request, accountId);
    }
}
