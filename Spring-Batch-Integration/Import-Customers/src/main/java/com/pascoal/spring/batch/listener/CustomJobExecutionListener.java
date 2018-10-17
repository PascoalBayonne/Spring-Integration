package com.pascoal.spring.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

public class CustomJobExecutionListener implements JobExecutionListener, InitializingBean {

    private static final Logger logger = Logger.getLogger(CustomJobExecutionListener.class.getName());
    private static final String COMPLETED_CODE ="COMPLETED";

    private Path processedFolderLocation;
    private Path inputFilename;

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (COMPLETED_CODE.equalsIgnoreCase(jobExecution.getExitStatus().getExitCode())){
            logger.info("Execution after Job");
            moveFileToProcessedFolder(inputFilename, processedFolderLocation);
        }
    }

    private void moveFileToProcessedFolder(Path inputFilename, Path processedFolderLocation) {
        logger.info("Moving the processed file to new folder: PROCESSED_DIRECTORY");
        if (Files.notExists(processedFolderLocation)) {
            try {
                Files.createDirectory(processedFolderLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.move(inputFilename.toAbsolutePath(), processedFolderLocation.resolve(inputFilename.getFileName() + "_PROCESSED"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.info("Error while trying to move the processed file: ");
            logger.info(e.getMessage());
        }
    }

    public void setProcessedFolderLocation(Path processedFolderLocation) {
        this.processedFolderLocation = processedFolderLocation;
    }

    public void setInputFilename(Path inputFilename) {
        this.inputFilename = inputFilename;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
