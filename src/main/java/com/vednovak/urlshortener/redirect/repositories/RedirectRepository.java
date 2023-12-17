package com.vednovak.urlshortener.redirect.repositories;

import com.vednovak.urlshortener.register.models.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RedirectRepository extends JpaRepository<ShortenedUrl, Long> {
    Optional<ShortenedUrl> findByUrlShortened(String urlShortened);
}
