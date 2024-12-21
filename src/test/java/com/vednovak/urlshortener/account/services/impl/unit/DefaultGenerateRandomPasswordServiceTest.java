package com.vednovak.urlshortener.account.services.impl.unit;

import com.vednovak.urlshortener.account.services.impl.DefaultGenerateRandomPasswordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class DefaultGenerateRandomPasswordServiceTest {

    private final DefaultGenerateRandomPasswordService passwordService = new DefaultGenerateRandomPasswordService();

    @Test
    void generateRandomPassword() {
        final String password = passwordService.generateRandomPassword();
        assertNotNull(password);
        assertTrue(password.length() >= 8);
    }
}