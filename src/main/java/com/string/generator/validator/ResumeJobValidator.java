package com.string.generator.validator;

import com.string.generator.model.job.Job;
import com.string.generator.model.job.JobRepository;
import com.string.generator.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ResumeJobValidator extends AbstractValidator implements ExistingJobValidatorInterface
{
    private final ConfigurableEnvironment config;
    private final JobRepository jobRepository;

    @Autowired
    public ResumeJobValidator(JobRepository jobRepository, ConfigurationService configurationService)
    {
        this.jobRepository = jobRepository;
        this.config = configurationService.properties();
    }

    public boolean isValid(long jobId)
    {
        this.errorMessages.clear();
        boolean jobValid = this.validateJob(jobId);
        if (!jobValid) {
            this.errorMessages.add(this.config.getProperty("resume.job.invalid.id"));
        }

        return jobValid;
    }

    private boolean validateJob(long jobId)
    {
        Optional<Job> currentJob = this.jobRepository.findById(jobId);
        return currentJob.isPresent() && currentJob.get().getActive();
    }
}
