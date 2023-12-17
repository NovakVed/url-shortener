package com.vednovak.urlshortener.message.services.impl;

import com.vednovak.urlshortener.message.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class DefaultMessageService implements MessageService {

    private final MessageSource messageSource;

    @Autowired
    public DefaultMessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }
}
