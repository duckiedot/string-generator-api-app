package com.string.generator.validator;

import com.string.generator.model.job.Job;
import com.string.generator.model.job.JobRepository;
import com.string.generator.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DownloadValidator extends AbstractValidator implements ExistingJobValidatorInterface
{
    private final JobRepository jobRepository;

    private final ConfigurableEnvironment config;

    @Autowired
    public DownloadValidator(JobRepository jobRepository, ConfigurationService configurationService)
    {
        this.jobRepository = jobRepository;
        this.config = configurationService.properties();
    }

    public boolean isValid(long jobId)
    {
        this.errorMessages.clear();
        Optional<Job> currentJob = this.jobRepository.findById(jobId);

        if (currentJob.isEmpty()) {
            this.errorMessages.add(this.config.getProperty("download.job.invalid.id"));
            return false;
        }

        if (currentJob.get().getActive() == 1) {
            this.errorMessages.add(this.config.getProperty("download.job.not.finished"));
            return false;
        }

        return true;
    }
}
