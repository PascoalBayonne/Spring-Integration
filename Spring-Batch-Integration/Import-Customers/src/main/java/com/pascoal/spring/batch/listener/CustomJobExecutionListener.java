package com.pascoal.spring.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@Component
public class CustomJobExecutionListener implements JobExecutionListener,InitializingBean {

    private static final Logger logger = Logger.getLogger(CustomJobExecutionListener.class.getName());
    private static final String COMPLETED_STATUS = "COMPLETED";

    private File fileOutput;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Job started as follow :" + jobExecution.getExecutionContext());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        JobParameters jobParameters = jobExecution.getJobParameters();
        String filename = jobParameters.getString("input.file.name");
        
        if (filename.isEmpty()){
            return;
        }
        
        Path sourceFile = Paths.get(filename);
        Path targetFolder = fileOutput.toPath();

        logger.info(jobExecution.getExitStatus().getExitCode());

        if (COMPLETED_STATUS.equalsIgnoreCase(jobExecution.getExitStatus().getExitCode())) {
            try {
                moveFileToProcessedFolder(sourceFile, targetFolder);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }

    }

    private void moveFileToProcessedFolder(Path sourceFile, Path targetFolder) {
        logger.info("Moving file to processed folder");

        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            Files.move(sourceFile, targetFolder.resolve(LocalDateTime.now().format(dateTimeFormatter) + "_DONE"), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            logger.warning("Couldn't move the file");
            e.printStackTrace();
        }
    }

    public void setFileOutput(File fileOutput) {
        this.fileOutput = fileOutput;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(fileOutput, new StringBuilder().append("The path ")
                .append(fileOutput.getAbsolutePath())
                .append(" doesn't exists. Please provide it")
                .toString());
    }
}
