package com.string.generator.validator;

import com.string.generator.model.job.Job;
import com.string.generator.model.job.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ResumeJobValidator extends AbstractValidator implements ExistingJobValidatorInterface
{
    private final String ERROR_INVALID_RESUME_ID =
            "Job with requested ID either doesn't exist, or is already finished";
    private final JobRepository jobRepository;

    @Autowired
    public ResumeJobValidator(JobRepository jobRepository)
    {
        this.jobRepository = jobRepository;
    }

    public boolean isValid(long jobId)
    {
        this.errorMessages.clear();
        boolean jobValid = this.validateJob(jobId);
        if (!jobValid) {
            this.errorMessages.add(this.ERROR_INVALID_RESUME_ID);
        }

        return jobValid;
    }

    private boolean validateJob(long jobId)
    {
        Optional<Job> currentJob = this.jobRepository.findById(jobId);
        return currentJob.isPresent() && currentJob.get().getActive() == 1;
    }
}
