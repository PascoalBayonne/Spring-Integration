package com.pascoal.filters;

import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;

import java.io.File;
import java.util.logging.Logger;

public class CustomFileFilter implements MessageSelector {
    private static final Logger LOGGER = Logger.getLogger(CustomFileFilter.class.getName());

    public boolean accept(Message<?> message) {
        LOGGER.info("Filtering the message with: "+message.getPayload().toString());
        return (message.getPayload() instanceof File && ((File) message.getPayload()).getName().startsWith("customers"));
    }
}
