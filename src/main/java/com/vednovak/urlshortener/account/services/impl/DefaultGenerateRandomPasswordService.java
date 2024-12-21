package com.vednovak.urlshortener.account.services.impl;

import com.vednovak.urlshortener.account.services.GenerateRandomPasswordService;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;

import static com.vednovak.urlshortener.account.utils.AccountConstants.PASSWORD_LENGTH;

@Service
public class DefaultGenerateRandomPasswordService implements GenerateRandomPasswordService {

    @Override
    public String generateRandomPassword() {
        final CharacterRule alphabeticRule = new CharacterRule(EnglishCharacterData.Alphabetical);
        final CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit);
        final PasswordGenerator passwordGenerator = new PasswordGenerator();
        return passwordGenerator.generatePassword(PASSWORD_LENGTH, alphabeticRule, digitRule);
    }
}