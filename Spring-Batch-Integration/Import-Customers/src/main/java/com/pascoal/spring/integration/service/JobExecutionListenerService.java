package com.pascoal.spring.integration.service;

import org.springframework.batch.core.JobExecution;

import java.util.concurrent.Future;

public interface JobExecutionListenerService {
     Future<JobExecution> computeExecutionListener(JobExecution jobExecution);
}
