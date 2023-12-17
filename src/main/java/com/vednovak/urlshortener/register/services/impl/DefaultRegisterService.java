package com.vednovak.urlshortener.register.services.impl;

import com.vednovak.urlshortener.account.models.Account;
import com.vednovak.urlshortener.account.repositories.AccountRepository;
import com.vednovak.urlshortener.register.models.RegisterRequest;
import com.vednovak.urlshortener.register.models.RegisterResponse;
import com.vednovak.urlshortener.register.models.ShortenedUrl;
import com.vednovak.urlshortener.register.repositories.RegisterRepository;
import com.vednovak.urlshortener.register.services.RegisterService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.vednovak.urlshortener.register.utils.RegisterConstants.*;

@Service
public class DefaultRegisterService implements RegisterService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultRegisterService.class);

    private final RegisterRepository registerRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public DefaultRegisterService(RegisterRepository registerRepository, AccountRepository accountRepository) {
        this.registerRepository = registerRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public RegisterResponse shortenAndRegisterUrl(
            final RegisterRequest registerRequest,
            final HttpServletRequest request,
            final String accountId
    ) {
        final Account account = accountRepository.findByAccountId(accountId);
        final String url = registerRequest.getUrl();
        if (registerRepository.existsByUrlAndAccountAccountId(url, accountId)) {
            String shortenedUrl = registerRepository.findByUrlAndAccountAccountId(url, accountId).getUrlShortened();
            return new RegisterResponse(StringUtils.join(getBaseUrl(request), SLASH, shortenedUrl));
        }

        final String shortenedUrl = generateUniqueShortenedUrl();
        final ShortenedUrl register = ShortenedUrl.builder()
                .url(registerRequest.getUrl())
                .urlShortened(shortenedUrl)
                .account(account)
                .redirectType(registerRequest.getRedirectType())
                .visitCount(0L)
                .build();

        registerRepository.save(register);

        return new RegisterResponse(StringUtils.join(getBaseUrl(request), SLASH, shortenedUrl));
    }

    private String generateUniqueShortenedUrl() {
        String shortenUrl;
        do {
            shortenUrl = generateShortenUrl();
        } while (registerRepository.existsByUrlShortened(shortenUrl));
        return shortenUrl;
    }

    private String generateShortenUrl() {
        return RandomStringUtils.randomAlphabetic(SHORTEN_URL_LENGTH);
    }

    private String getBaseUrl(HttpServletRequest request) {
        final String scheme = request.getScheme();
        final String serverName = request.getServerName();
        final int serverPort = request.getServerPort();

        return StringUtils.join(scheme, SCHEME_SEPARATOR, serverName, COLON, serverPort);
    }
}
