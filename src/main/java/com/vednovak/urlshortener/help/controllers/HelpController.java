package com.vednovak.urlshortener.help.controllers;

import com.vednovak.urlshortener.constants.SwaggerConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.vednovak.urlshortener.utils.HeaderValues.CONNECTION_CLOSE;

@Tag(name = "Help", description = "the Documentation API")
@RestController
@RequestMapping(HelpController.ENDPOINT)
public class HelpController {

    protected static final String ENDPOINT = "/v1/helps";

    @Operation(
            summary = "Redirects to Swagger UI Documentation",
            description = "contains instructions of installation, start and usage of Spring Boot app")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerConstants.OK, description = "Documentation Found - Successful operation"),
            @ApiResponse(responseCode = SwaggerConstants.INTERNAL_SERVER_ERROR, description = "Internal server error"),
    })
    @GetMapping
    public ResponseEntity<String> redirectToDocumentation() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/swagger-ui/index.html")
                .header(HttpHeaders.CONNECTION, CONNECTION_CLOSE)
                .build();
    }
}
