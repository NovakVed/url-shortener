package com.vednovak.urlshortener.statistic.services.impl;

import com.vednovak.urlshortener.register.models.ShortenedUrl;
import com.vednovak.urlshortener.statistic.repositories.StatisticRepository;
import com.vednovak.urlshortener.statistic.services.StatisticService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DefaultStatisticService implements StatisticService {

    private final StatisticRepository statisticRepository;

    public DefaultStatisticService(final StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    @Override
    public Map<String, Long> getRegisteredUrlsWithVisitCount(String accountId) {
        final List<ShortenedUrl> shortenedUrls = statisticRepository.findByAccountAccountId(accountId);

        return shortenedUrls.stream()
                .collect(Collectors.toMap(ShortenedUrl::getUrl, ShortenedUrl::getVisitCount));
    }
}
