package com.string.generator.validator;

import com.string.generator.model.job.Job;
import com.string.generator.model.job.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DownloadValidator extends AbstractValidator implements ExistingJobValidatorInterface
{
    private final String ERROR_MSG_INVALID_ID = "Job with given ID doesn't exist";
    private final String ERROR_MSG_JOB_NOT_FINISHED = "Job with given ID is not finished";

    private final JobRepository jobRepository;

    @Autowired
    public DownloadValidator(JobRepository jobRepository)
    {
        this.jobRepository = jobRepository;
    }

    public boolean isValid(long jobId)
    {
        this.errorMessages.clear();
        Optional<Job> currentJob = this.jobRepository.findById(jobId);

        if (currentJob.isEmpty()) {
            this.errorMessages.add(this.ERROR_MSG_INVALID_ID);
            return false;
        }

        if (currentJob.get().getActive() == 1) {
            this.errorMessages.add(this.ERROR_MSG_JOB_NOT_FINISHED);
            return false;
        }

        return true;
    }
}
