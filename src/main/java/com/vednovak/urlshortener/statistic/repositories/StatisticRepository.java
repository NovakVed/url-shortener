package com.vednovak.urlshortener.statistic.repositories;

import com.vednovak.urlshortener.register.models.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatisticRepository extends JpaRepository<ShortenedUrl, Long> {
    List<ShortenedUrl> findByAccountAccountId(String accountId);
}
