package com.pascoal.spring.integration.transformer;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

import java.io.File;

public class FileMessageToJobRequest implements InitializingBean{

    private Job job;
    private String fileParameterName;


    public void setJob(Job job) {
        this.job = job;
    }

    public void setFileParameterName(String fileParameterName) {
        this.fileParameterName = fileParameterName;
    }

    @Transformer
    public JobLaunchRequest jobLaunchRequest(Message<File> fileMessage){
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString(fileParameterName,fileMessage.getPayload().getAbsolutePath());
        return new JobLaunchRequest(job,jobParametersBuilder.toJobParameters());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(fileParameterName,"file doesn't exists");
        Assert.notNull(job, "A Job must be provided");
    }
}
