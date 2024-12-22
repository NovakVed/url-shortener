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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.vednovak.urlshortener.register.utils.RegisterConstants.*;

@Service
public class DefaultRegisterService implements RegisterService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultRegisterService.class);

    private final RegisterRepository registerRepository;
    private final AccountRepository accountRepository;

    public DefaultRegisterService(final RegisterRepository registerRepository, final AccountRepository accountRepository) {
        this.registerRepository = registerRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public RegisterResponse shortenAndRegisterUrl(
            final RegisterRequest registerRequest,
            final HttpServletRequest request,
            final String accountId
    ) throws UsernameNotFoundException {
        final Account account = getAccountOrElseThrow(accountId);
        final String url = registerRequest.getUrl();
        if (urlAlreadyExists(accountId, url)) {
            return createRegisterResponseForExistingUrl(request, accountId, url);
        }

        final String shortenedUrl = generateUniqueShortenedUrl();
        saveShortenedUrl(registerRequest, shortenedUrl, account);

        return new RegisterResponse(StringUtils.join(getBaseUrl(request), SLASH, shortenedUrl));
    }

    private Account getAccountOrElseThrow(final String accountId) throws UsernameNotFoundException {
        return accountRepository
                .findByAccountId(accountId)
                .orElseThrow(() -> {
                    LOG.error("User managed to authenticate but now his account '{}' is able to be found", accountId);
                    return new UsernameNotFoundException(USER_NOT_FOUND);
                });
    }

    private boolean urlAlreadyExists(final String accountId, final String url) {
        return registerRepository.existsByUrlAndAccountAccountId(url, accountId);
    }

    private RegisterResponse createRegisterResponseForExistingUrl(final HttpServletRequest request, final String accountId, final String url) {
        final String shortenedUrl = registerRepository.findByUrlAndAccountAccountId(url, accountId).getUrlShortened();
        return new RegisterResponse(StringUtils.join(getBaseUrl(request), SLASH, shortenedUrl));
    }

    private void saveShortenedUrl(final RegisterRequest registerRequest, final String shortenedUrl, final Account account) {
        final ShortenedUrl register = ShortenedUrl.builder()
                .url(registerRequest.getUrl())
                .urlShortened(shortenedUrl)
                .account(account)
                .redirectType(registerRequest.getRedirectType())
                .visitCount(0L)
                .build();

        registerRepository.save(register);
    }

    // Maybe think about how you can tackle this?
    // TODO: potential vulnerability
    //  -> what if someone (potential attacker) decides to register quite a big number of urls? Then those collisions would happened more often + potential overflow + others users can't register urls to be shortened?
    //  -> Do we notify a user about the problem? What if because of collisions this takes for ever to finish?
    // TODO: also if you don't delete registered websites after a certain period of time you will end up with those collisions more often + overflow!
    // TODO: should number constraint be set to handle potential overflow?
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
