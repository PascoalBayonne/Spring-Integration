package com.pascoal.spring.integration.service;

import org.springframework.batch.core.JobExecution;

import java.util.logging.Logger;

public class JobExecutionListenerServiceImplement {
    private final Logger logger = Logger.getLogger(JobExecutionListenerServiceImplement.class.getName());

    void displayExecutionListener(JobExecution jobExecution) {
        logger.info(jobExecution.getExitStatus().getExitCode());
    }
}
