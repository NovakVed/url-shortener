package com.vednovak.urlshortener.redirect.services.impl.unit;

import com.vednovak.urlshortener.message.services.MessageService;
import com.vednovak.urlshortener.redirect.exceptions.RedirectNullException;
import com.vednovak.urlshortener.redirect.repositories.RedirectRepository;
import com.vednovak.urlshortener.redirect.services.impl.DefaultRedirectService;
import com.vednovak.urlshortener.register.models.ShortenedUrl;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultRedirectServiceTest {

    @Mock
    private RedirectRepository redirectRepository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private DefaultRedirectService redirectService;

    @Test
    void getOriginalUrlByShortenedUrlShouldReturnUrlAndRedirectType() throws RedirectNullException {
        // Given
        final String shortenedUrl = "abc123";
        final ShortenedUrl mockedShortenedUrl = ShortenedUrl.builder()
                .url("https://example.com")
                .redirectType(301)
                .build();

        when(redirectRepository.findByUrlShortened(shortenedUrl)).thenReturn(Optional.of(mockedShortenedUrl));

        // When
        final Pair<String, Integer> result = redirectService.getOriginalUrlByShortenedUrl(shortenedUrl);

        // Then
        assertEquals("https://example.com", result.getLeft());
        assertEquals(301, result.getRight());
    }

    @Test
    void getOriginalUrlByShortenedUrlShouldThrowRedirectNullExceptionWhenUrlNotFound() {
        // Given
        final String shortenedUrl = "https://www.notexisting.com";
        when(redirectRepository.findByUrlShortened(shortenedUrl)).thenReturn(Optional.empty());

        // When and Then
        assertThrowsExactly(RedirectNullException.class, () -> redirectService.getOriginalUrlByShortenedUrl(shortenedUrl));
    }
}