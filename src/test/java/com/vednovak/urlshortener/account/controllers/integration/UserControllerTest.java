package com.vednovak.urlshortener.account.controllers.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vednovak.urlshortener.account.models.Account;
import com.vednovak.urlshortener.account.models.AccountRequest;
import com.vednovak.urlshortener.account.repositories.AccountRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void registerShouldRegisterNewAccount() throws Exception {
        // given
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountId("vednovaK");

        // when
        performRegistration(accountRequest, status().isOk());

        // then
        assertTrue(accountRepository.existsByAccountId(accountRequest.getAccountId()));
    }

    @Test
    void registerShouldResultInBadRequestOnAlreadyRegisteredUser() throws Exception {
        // given
        final Account user = new Account("exists", "{bcrypt}$2a$10$6dFU7k9acy0lLzvP/.mN6Of7CBAYkvaF/bnZPuUVhvlJIJ2.hGzcy\n");
        accountRepository.save(user);

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountId("exists");

        // when
        performRegistration(accountRequest, status().isBadRequest());

        // then
        assertTrue(accountRepository.existsByAccountId(accountRequest.getAccountId()));
    }

    @Test
    void registerShouldResultInBadRequestOnAccountRequestValidationForBlankUsername() throws Exception {
        // given
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountId(StringUtils.EMPTY);

        // when
        performRegistration(accountRequest, status().isBadRequest());

        // then
        assertFalse(accountRepository.existsByAccountId(accountRequest.getAccountId()));
    }

    @Test
    void registerShouldResultInBadRequestOnAccountRequestValidationForNullUsername() throws Exception {
        // given
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountId(null);

        // when
        performRegistration(accountRequest, status().isBadRequest());

        // then
        assertFalse(accountRepository.existsByAccountId(accountRequest.getAccountId()));
    }

    @Test
    void registerShouldResultInBadRequestOnAccountRequestValidationForSmallUsernameSize() throws Exception {
        // given
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountId("ab");

        // when
        performRegistration(accountRequest, status().isBadRequest());

        // then
        assertFalse(accountRepository.existsByAccountId(accountRequest.getAccountId()));
    }

    @Test
    void registerShouldResultInBadRequestOnAccountRequestValidationForLargeUsernameSize() throws Exception {
        // given
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountId("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");

        // when
        performRegistration(accountRequest, status().isBadRequest());

        // then
        assertFalse(accountRepository.existsByAccountId(accountRequest.getAccountId()));
    }

    @Test
    void registerShouldResultInBadRequestOnAccountRequestValidationForInvalidCharactersUsernameSize() throws Exception {
        // given
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountId("vedno1k");

        // when
        performRegistration(accountRequest, status().isBadRequest());

        // then
        assertFalse(accountRepository.existsByAccountId(accountRequest.getAccountId()));
    }

    // TODO: maybe extract this into some utils class as you might use this a lot actually?
    // TODO: maybe implement it for all (get/post/delete/put/patch) http requests?
    private void performRegistration(final AccountRequest accountRequest, final ResultMatcher status) throws Exception {
        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}