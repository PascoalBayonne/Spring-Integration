package com.pascoal;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;

import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        String[] springConfigurations = {"context.xml"};

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF//si-components.xml","META-INF//job-config.xml");

        //Creating jobLauncher
//        JobLauncher jobLauncher = (JobLauncher) applicationContext.getBean("jobLauncher");
//
//        //creating the Job
//        Job importCustomerJob = (Job) applicationContext.getBean("importCustomers");
//
//        //executing the job
//        JobExecution jobExecution = jobLauncher.run(importCustomerJob, new JobParameters());

        //LOGGER.info("Exit status: " + jobExecution.getStatus());

        QueueChannel completeApplication = applicationContext.getBean("completeApplication", QueueChannel.class);
        final Message<JobExecution>jobExecutionMessage = (Message<JobExecution>) completeApplication.receive();
        JobExecution execution = jobExecutionMessage.getPayload();
        ExitStatus exitStatus = execution.getExitStatus();

        System.out.println(exitStatus.getExitDescription());
    }
}
