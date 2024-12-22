package com.vednovak.urlshortener.redirect.services.impl;

import com.vednovak.urlshortener.message.services.MessageService;
import com.vednovak.urlshortener.redirect.exceptions.RedirectNullException;
import com.vednovak.urlshortener.redirect.repositories.RedirectRepository;
import com.vednovak.urlshortener.redirect.services.RedirectService;
import com.vednovak.urlshortener.register.models.ShortenedUrl;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.vednovak.urlshortener.redirect.utils.RedirectConstants.REDIRECT_UNSUCCESSFUL;

@Service
public class DefaultRedirectService implements RedirectService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultRedirectService.class);

    private final RedirectRepository redirectRepository;
    private final MessageService messageService;

    public DefaultRedirectService(final RedirectRepository redirectRepository, final MessageService messageService) {
        this.redirectRepository = redirectRepository;
        this.messageService = messageService;
    }

    @Override
    public Pair<String, Integer> getOriginalUrlByShortenedUrl(String shortenedUrl) throws RedirectNullException {
        final ShortenedUrl registeredUrl = getRegisteredUrlOrElseThrow(shortenedUrl);

        updateUrlVisitCount(registeredUrl);
        return Pair.of(registeredUrl.getUrl(), registeredUrl.getRedirectType());
    }

    private ShortenedUrl getRegisteredUrlOrElseThrow(final String shortenedUrl) throws RedirectNullException {
        return redirectRepository
                .findByUrlShortened(shortenedUrl)
                .orElseThrow(() -> {
                    LOG.warn("Redirection unsuccessful for shortened URL: {}", shortenedUrl);
                    return new RedirectNullException(messageService.getMessage(REDIRECT_UNSUCCESSFUL));
                });
    }

    private void updateUrlVisitCount(final ShortenedUrl originalUrl) {
        final long incrementVisitCount = originalUrl.getVisitCount() + 1;
        originalUrl.setVisitCount(incrementVisitCount);
        redirectRepository.save(originalUrl);
    }
}
