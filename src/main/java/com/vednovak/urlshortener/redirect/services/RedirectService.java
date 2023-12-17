package com.vednovak.urlshortener.redirect.services;

import com.vednovak.urlshortener.redirect.exceptions.RedirectNullException;
import org.apache.commons.lang3.tuple.Pair;

public interface RedirectService {
    Pair<String, Integer> getOriginalUrlByShortenedUrl(String shortenedUrl) throws RedirectNullException;
}
