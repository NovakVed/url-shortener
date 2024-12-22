package com.vednovak.urlshortener.account.controllers;

import com.vednovak.urlshortener.account.exceptions.AccountRegisterException;
import com.vednovak.urlshortener.account.models.AccountRequest;
import com.vednovak.urlshortener.account.models.AccountResponse;
import com.vednovak.urlshortener.account.services.AccountService;
import com.vednovak.urlshortener.constants.SwaggerConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Account", description = "the Account Register API")
@RestController
@Validated
public class UserController {

    @Autowired
    AccountService accountService;

    @Operation(
            summary = "Register a new account",
            description = "redirects to the original URL associated with the provided shortened URL and updates the visit count of that redirected URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerConstants.OK, description = "Account registered successfully"),
            @ApiResponse(responseCode = SwaggerConstants.BAD_REQUEST, description = "Bad request - Invalid input data"),
            @ApiResponse(responseCode = SwaggerConstants.INTERNAL_SERVER_ERROR, description = "Internal server error")
    })
    @PostMapping("/account")
    public AccountResponse registerAccount(@RequestBody @Valid AccountRequest accountRequest) throws AccountRegisterException {
        return accountService.register(accountRequest);
    }
}
