package com.arpit.spring.integration.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.logging.Logger;

@Service
public class FileInServiceActivator {
    private Logger logger = Logger.getLogger(FileInServiceActivator.class.getName());

    @ServiceActivator(inputChannel = "fileIn")
    public void run(File file) {
        String fileName = file.getAbsolutePath();
        System.out.println("File to be processed" + fileName);

        readFiles(file);

    }

    private void readFiles(File file) {

        System.out.println("\nReading file ...");

        String lines = null;
        Charset utf8 = StandardCharsets.UTF_8;

        try (BufferedReader bufferedReader = Files.newBufferedReader(file.toPath(),utf8)) {
            while ((lines = bufferedReader.readLine()) != null) {
                System.out.println(lines);
            }
        } catch (IOException e) {
            logger.info("error msg: " + e);
        }
    }
}
