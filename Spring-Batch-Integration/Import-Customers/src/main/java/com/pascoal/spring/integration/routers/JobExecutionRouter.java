package com.pascoal.spring.integration.routers;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.integration.annotation.Router;

import java.util.ArrayList;
import java.util.List;

public class JobExecutionRouter {

    @Router
    public List<String> executionRouter(JobExecution jobExecution) {
        final List<String> routeToValidChannel = new ArrayList<>();

        if (jobExecution.getExitStatus().equals(BatchStatus.FAILED)) {
            //in case of failed move to error folder and append error
            routeToValidChannel.add("sendErrorToFolder");
        } else {
            if (jobExecution.getExitStatus().equals(BatchStatus.COMPLETED)) {
                routeToValidChannel.add("completeApplication");
            }
            routeToValidChannel.add("sendEmailNotification");
        }
        return routeToValidChannel;
    }
}
