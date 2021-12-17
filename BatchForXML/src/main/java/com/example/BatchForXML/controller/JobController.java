package com.example.BatchForXML.controller;

import com.example.BatchForXML.exceptions.ProcessException;
import com.example.BatchForXML.exceptions.ReadException;
import com.example.BatchForXML.exceptions.WriteException;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@EnableBatchProcessing
@Log4j2
public class JobController {

    static final String INPUT_FILE_PATH = "src/main/resources/data/students.xml";

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job1;

    @PostConstruct
    public void init() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("inputPath", INPUT_FILE_PATH)
                    .toJobParameters();
            JobExecution execution = jobLauncher.run(job1,
                    jobParameters);
            ExitStatus status = execution.getExitStatus();
            handleError(execution, status);
        } catch (ReadException e) {
            log.error("Error when reading message, verify the format...");
        } catch (ProcessException e) {
            log.error("Error when processing message, verify functional constraints...");
        } catch (WriteException e) {
            log.error("Error when writing into database, retry or verify tables constraints...");
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    private void handleError(JobExecution execution, ExitStatus status)
            throws ReadException, ProcessException, WriteException {

        if (status.getExitCode().equals(ExitStatus.COMPLETED.getExitCode())) {
            log.info("Job finished successfully");
        } else {
            log.warn("Job encountered a problem");

            List<Throwable> exceptions = execution
                    .getAllFailureExceptions();

            for (Throwable throwable : exceptions) {

                Throwable currentThrowable = throwable;
                while (currentThrowable != null) {

                    if (currentThrowable instanceof ReadException) {
                        throw (ReadException) currentThrowable;
                    }
                    if (currentThrowable instanceof ProcessException) {
                        throw (ProcessException) currentThrowable;
                    }
                    if (currentThrowable instanceof WriteException) {
                        throw (WriteException) currentThrowable;
                    }
                    currentThrowable = currentThrowable.getCause();
                }

                log.error("An unexpected error when executing the job: " + throwable);
            }
        }
    }
}
