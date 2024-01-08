package com.vednovak.urlshortener.message.services.impl;

import com.vednovak.urlshortener.message.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class DefaultMessageService implements MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultMessageService.class);

    private final MessageSource messageSource;

    @Autowired
    public DefaultMessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String key) {
        try {
            return messageSource.getMessage(key, null, Locale.getDefault());
        } catch (NoSuchMessageException ex) {
            LOG.debug("Message key '{}' not found.", key);
            return "Message not found for key: " + key;
        }
    }
}
