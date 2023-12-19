package com.vednovak.urlshortener.register.services.impl;

import com.vednovak.urlshortener.account.models.Account;
import com.vednovak.urlshortener.account.repositories.AccountRepository;
import com.vednovak.urlshortener.register.models.RegisterRequest;
import com.vednovak.urlshortener.register.models.RegisterResponse;
import com.vednovak.urlshortener.register.models.ShortenedUrl;
import com.vednovak.urlshortener.register.repositories.RegisterRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultRegisterServiceTest {

    @Mock
    private RegisterRepository registerRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private DefaultRegisterService registerService;

    @Test
    void shortenAndRegisterUrlShouldGenerateShortenedUrl() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUrl("https://example.com");
        registerRequest.setRedirectType(HttpStatus.FOUND.value());

        Account account = new Account();
        account.setAccountId("testUser");

        when(request.getScheme()).thenReturn("https");
        when(request.getServerName()).thenReturn("short");
        when(request.getServerPort()).thenReturn(8080);
        when(accountRepository.findByAccountId(anyString())).thenReturn(Optional.of(account));
        when(registerRepository.existsByUrlAndAccountAccountId(anyString(), anyString())).thenReturn(false);
        when(registerRepository.existsByUrlShortened(anyString())).thenReturn(false);
        when(registerRepository.save(any(ShortenedUrl.class))).thenReturn(new ShortenedUrl());

        // When
        RegisterResponse response = registerService.shortenAndRegisterUrl(registerRequest, request, "testUser");

        // Then
        assertNotNull(response);
        assertNotNull(response.getShortUrl());
        assertTrue(response.getShortUrl().contains("https://short:8080/"));

        // Verify that the save method is called
        verify(registerRepository, times(1)).save(any(ShortenedUrl.class));
    }

    @Test
    void shortenAndRegisterUrlShouldReturnExistingShortenedUrl() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUrl("https://example.com");
        registerRequest.setRedirectType(HttpStatus.FOUND.value());

        Account account = new Account();
        account.setAccountId("testUser");

        ShortenedUrl existingShortenedUrl = new ShortenedUrl();
        existingShortenedUrl.setUrlShortened("existingShortUrl");

        when(request.getScheme()).thenReturn("https");
        when(request.getServerName()).thenReturn("short");
        when(request.getServerPort()).thenReturn(8080);
        when(accountRepository.findByAccountId(anyString())).thenReturn(Optional.of(account));
        when(registerRepository.existsByUrlAndAccountAccountId(anyString(), anyString())).thenReturn(true);
        when(registerRepository.findByUrlAndAccountAccountId(anyString(), anyString())).thenReturn(existingShortenedUrl);

        // When
        RegisterResponse response = registerService.shortenAndRegisterUrl(registerRequest, request, "testUser");

        // Then
        assertNotNull(response);
        assertNotNull(response);
        assertNotNull(response.getShortUrl());
        assertEquals("https://short:8080/existingShortUrl", response.getShortUrl());

        // Verify that the save method is not called
        verify(registerRepository, never()).save(any(ShortenedUrl.class));
    }
}