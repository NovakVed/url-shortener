package com.vednovak.urlshortener.statistic.controllers;

import com.vednovak.urlshortener.constants.SwaggerConstants;
import com.vednovak.urlshortener.statistic.services.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.vednovak.urlshortener.utils.RegexPatterns.ALPHABETIC_PATTERN;

@Tag(name = "Statistic", description = "the Statistic Registered URLs of Account API")
@SecurityScheme(
        name = "Basic Auth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@RestController
@RequestMapping(StatisticController.ENDPOINT)
@Validated
public class StatisticController {

    protected static final String ENDPOINT = "/api/v1/statistics";

    private final StatisticService statisticService;

    public StatisticController(final StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Operation(
            summary = "Get Registered URLs by Account ID",
            description = "retrieves all registered URLs for the specified account ID",
            security = @SecurityRequirement(name = "Basic Auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerConstants.OK, description = "successful operation"),
            @ApiResponse(responseCode = SwaggerConstants.BAD_REQUEST, description = "Bad request - Invalid input data"),
            @ApiResponse(responseCode = SwaggerConstants.INTERNAL_SERVER_ERROR, description = "Internal server error")
    })
    @GetMapping("/{accountId}")
    public Map<String, Long> getRegisteredUrlsWithVisitCount(
            @PathVariable("accountId")
            @NotBlank(message = "Account ID cannot be blank")
            @NotNull(message = "Account ID cannot be null")
            @Size(min = 3, max = 30, message = "Account ID length must be between 3 and 30 characters")
            @Pattern(regexp = ALPHABETIC_PATTERN, message = "Account ID must contain only alphabetical characters")
            String accountId
    ) {
        return statisticService.getRegisteredUrlsWithVisitCount(accountId);
    }
}
