package com.vednovak.urlshortener.statistic.services;

import java.util.Map;

public interface StatisticService {
    Map<String, Long> getRegisteredUrlsWithVisitCount(String accountId);
}
