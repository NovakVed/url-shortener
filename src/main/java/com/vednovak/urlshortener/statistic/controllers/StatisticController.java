package com.vednovak.urlshortener.statistic.controllers;

import com.vednovak.urlshortener.statistic.services.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Statistic", description = "the Statistic Registered URLs of Account API")
@SecurityScheme(
        name = "Basic Auth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@RestController
@Validated
public class StatisticController {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticController.class);

    @Autowired
    private StatisticService statisticService;

    @Operation(
            summary = "Get Registered URLs by Account ID",
            description = "retrieves all registered URLs for the specified account ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
            // TODO: add more API responses
    })
    @GetMapping("/statistic/{AccountId}")
    // TODO: add invalid message here! Use message.service instead of hardcoded string value
    // TODO: you are already using this regex pattern, why not put it in some constants util?
    public Map<String, Long> getRegisteredUrlsWithVisitCount(
            @PathVariable("AccountId")
            @NotBlank(message = "Account ID cannot be blank")
            @NotNull(message = "Account ID cannot be null")
            @Size(min = 3, max = 30, message = "Account ID length must be between 3 and 30 characters")
            @Pattern(regexp = "^[A-Za-z]+$", message = "Account ID must contain only alphabetical characters")
            String accountId
    ) {
        return statisticService.getRegisteredUrlsWithVisitCount(accountId);
    }
}
