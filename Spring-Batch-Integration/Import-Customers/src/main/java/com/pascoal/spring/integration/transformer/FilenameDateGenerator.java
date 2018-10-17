package com.pascoal.spring.integration.transformer;

import org.springframework.integration.file.DefaultFileNameGenerator;
import org.springframework.messaging.Message;

import java.io.File;

public class FilenameDateGenerator extends DefaultFileNameGenerator {
    @Override
    public String generateFileName(Message<?> message) {
        File file = (File) message.getPayload();
        return super.generateFileName(message)+"_Processed";
    }
}
