package com.vednovak.urlshortener.statistic.services.impl.unit;

import com.vednovak.urlshortener.register.models.ShortenedUrl;
import com.vednovak.urlshortener.statistic.repositories.StatisticRepository;
import com.vednovak.urlshortener.statistic.services.impl.DefaultStatisticService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultStatisticServiceTest {

    @Mock
    private StatisticRepository statisticRepository;

    @InjectMocks
    private DefaultStatisticService statisticService;

    @Test
    void getRegisteredUrlsWithVisitCountShouldReturnMap() {
        // Given
        final String accountId = "testUser";
        final ShortenedUrl url1 = ShortenedUrl.builder().url("https://example.com/1")
                .urlShortened("short1")
                .redirectType(301)
                .visitCount(10L)
                .build();
        final ShortenedUrl url2 = ShortenedUrl.builder().url("https://example.com/2")
                .urlShortened("short2")
                .redirectType(302)
                .visitCount(20L)
                .build();

        final List<ShortenedUrl> shortenedUrls = Arrays.asList(url1, url2);

        when(statisticRepository.findByAccountAccountId(accountId)).thenReturn(shortenedUrls);

        // When
        final Map<String, Long> result = statisticService.getRegisteredUrlsWithVisitCount(accountId);

        // Then
        assertEquals(2, result.size());
        assertEquals(10L, result.get("https://example.com/1"));
        assertEquals(20L, result.get("https://example.com/2"));
    }

    @Test
    void getRegisteredUrlsWithVisitCountShouldReturnEmptyMapForNoUrls() {
        // Given
        final String accountId = "testUser";

        when(statisticRepository.findByAccountAccountId(accountId)).thenReturn(List.of());

        // When
        final Map<String, Long> result = statisticService.getRegisteredUrlsWithVisitCount(accountId);

        // Then
        assertEquals(0, result.size());
    }

}