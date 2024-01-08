package com.vednovak.urlshortener.account.services.impl.unit;

import com.vednovak.urlshortener.account.exceptions.AccountRegisterException;
import com.vednovak.urlshortener.account.models.Account;
import com.vednovak.urlshortener.account.models.AccountRequest;
import com.vednovak.urlshortener.account.models.AccountResponse;
import com.vednovak.urlshortener.account.repositories.AccountRepository;
import com.vednovak.urlshortener.account.services.impl.DefaultAccountService;
import com.vednovak.urlshortener.message.services.MessageService;
import org.apache.commons.lang3.BooleanUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.vednovak.urlshortener.account.utils.AccountConstants.CREATE_ACCOUNT_SUCCESSFUL;
import static com.vednovak.urlshortener.account.utils.AccountConstants.CREATE_ACCOUNT_UNSUCCESSFUL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAccountServiceUnitTest {

    @Mock
    private MessageService messageService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DefaultAccountService accountService;

    @Test
    void registerShouldRegisterNewAccount() throws AccountRegisterException {
        // Given
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountId("vednovak");
        when(accountRepository.existsByAccountId(accountRequest.getAccountId())).thenReturn(false);
        when(messageService.getMessage(CREATE_ACCOUNT_SUCCESSFUL)).thenReturn("something");
        when(passwordEncoder.encode(anyString())).thenReturn("hashpassword");

        // When
        AccountResponse accountResponse = accountService.register(accountRequest);

        // Then
        assertEquals(BooleanUtils.TRUE, accountResponse.getSuccess());
        assertEquals("something", accountResponse.getDescription());
        assertNotNull(accountResponse.getPassword());

        // Verify repository save method called
        verify(accountRepository, times(1)).save(new Account(accountRequest.getAccountId(), "hashpassword"));
        verify(accountRepository, times(1)).existsByAccountId("vednovak");
    }

    @Test
    void registerShouldThrowExceptionOnAlreadyRegisteredUser() throws AccountRegisterException {
        // Given
        AccountRequest accountRequest = new AccountRequest();
        when(accountRepository.existsByAccountId(accountRequest.getAccountId())).thenReturn(true);
        when(messageService.getMessage(CREATE_ACCOUNT_UNSUCCESSFUL)).thenReturn(anyString());

        // When
        assertThrowsExactly(AccountRegisterException.class, () -> accountService.register(accountRequest));

        // Then
        verify(accountRepository, never()).save(any());
    }
}