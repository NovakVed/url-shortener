package com.vednovak.urlshortener.redirect.services.impl;

import com.vednovak.urlshortener.redirect.exceptions.RedirectNullException;
import com.vednovak.urlshortener.redirect.repositories.RedirectRepository;
import com.vednovak.urlshortener.redirect.services.RedirectService;
import com.vednovak.urlshortener.register.models.ShortenedUrl;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultRedirectService implements RedirectService {

    private final RedirectRepository redirectRepository;

    @Autowired
    public DefaultRedirectService(RedirectRepository redirectRepository) {
        this.redirectRepository = redirectRepository;
    }

    @Override
    public Pair<String, Integer> getOriginalUrlByShortenedUrl(String shortenedUrl) throws RedirectNullException {
        final ShortenedUrl registeredUrl = redirectRepository
                .findByUrlShortened(shortenedUrl)
                .orElseThrow(() -> new RedirectNullException("Shortened URL not found!"));

        updateUrlVisitCount(registeredUrl);
        return Pair.of(registeredUrl.getUrl(), registeredUrl.getRedirectType());
    }

    private void updateUrlVisitCount(ShortenedUrl originalUrl) {
        originalUrl.setVisitCount(originalUrl.getVisitCount() + 1);
        redirectRepository.save(originalUrl);
    }
}
