package com.vednovak.urlshortener.register.repositories;

import com.vednovak.urlshortener.register.models.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepository extends JpaRepository<ShortenedUrl, Long> {
    boolean existsByUrlAndAccountAccountId(String url, String accountId);
    ShortenedUrl findByUrlAndAccountAccountId(String url, String accountId);
    boolean existsByUrlShortened(String urlShortened);
}
