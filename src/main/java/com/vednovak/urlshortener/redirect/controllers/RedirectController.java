package com.vednovak.urlshortener.redirect.controllers;

import com.vednovak.urlshortener.redirect.exceptions.RedirectNullException;
import com.vednovak.urlshortener.redirect.services.RedirectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.vednovak.urlshortener.utils.HeaderValues.CONNECTION_CLOSE;

@Tag(name = "Redirect", description = "the Redirect Shortened URL API")
@RestController
@Validated
public class RedirectController {

    @Autowired
    private RedirectService redirectService;

    @Operation(
            summary = "Redirect to Original URL",
            description = "redirects to the original URL associated with the provided shortened URL and updates the visit count of that redirected URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Redirect successful"),
            @ApiResponse(responseCode = "301", description = "Moved Permanently"),
            @ApiResponse(responseCode = "302", description = "Found"),
            @ApiResponse(responseCode = "404", description = "Not Found - Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @GetMapping("/{shortenedUrl}")
    public ResponseEntity<String> redirect(
            @PathVariable
            @Valid
            @Pattern(regexp = "^[A-Za-z]{6}$", message = "Shortened URL must be exactly 8 characters long and contain only alphabetical letters.")
            String shortenedUrl
    ) throws RedirectNullException {
        final Pair<String, Integer> urlAndHttpStatus = redirectService.getOriginalUrlByShortenedUrl(shortenedUrl);
        return ResponseEntity
                .status(urlAndHttpStatus.getRight())
                .header(HttpHeaders.LOCATION, urlAndHttpStatus.getLeft())
                .header(HttpHeaders.CONNECTION, CONNECTION_CLOSE)
                .build();
    }
}
