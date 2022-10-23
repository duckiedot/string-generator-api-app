package com.string.generator.controller;

import com.google.gson.Gson;
import com.string.generator.model.job.Job;
import com.string.generator.model.job.JobRepository;
import com.string.generator.validator.ResumeJobValidator;
import com.string.generator.validator.Validator;
import com.string.generator.model.processor.JobProcessor;
import com.string.generator.model.request.Request;
import com.string.generator.model.request.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class CreateJob
{
    private final String JOB_CREATED_MSG = "Job created successfully with ID: %o";

    private final RequestRepository requestRepository;
    private final JobRepository jobRepository;
    private final JobProcessor jobProcessor;
    private final Validator requestDataValidator;
    private final ResumeJobValidator resumeJobValidator;

    private Gson gson;

    @Autowired
    public CreateJob(
            RequestRepository requestRepository,
            JobRepository jobRepository,
            Validator validator,
            ResumeJobValidator resumeJobValidator,
            JobProcessor jobProcessor,
            Gson gson
    ) {
        this.requestRepository = requestRepository;
        this.jobRepository = jobRepository;
        this.requestDataValidator = validator;
        this.resumeJobValidator = resumeJobValidator;
        this.jobProcessor = jobProcessor;
        this.gson = gson;
    }

    @PostMapping(value = "rest/createjob")
    public String createJob(@RequestBody Job job) throws IOException {
        Request request = new Request();
        if (!(this.requestDataValidator.isValid(job))) {
            request.setErrorMessages(this.requestDataValidator.getErrorMessages());
            request.setIsValid(true);
            this.requestRepository.save(request);

            return this.gson.toJson(request.getErrorMessages());
        }
        request.setIsValid(false);
        this.requestRepository.save(request);
        this.jobRepository.save(job);
        this.jobProcessor.processJob(job, this.jobRepository);

        return String.format(this.JOB_CREATED_MSG, job.getId());
    }

    @PostMapping(value = "rest/resumejob")
    public String resumeJob(@RequestBody long jobId) throws IOException {
        Request request = new Request();

        if (!this.resumeJobValidator.isValid(jobId)) {
            request.setErrorMessages(this.resumeJobValidator.getErrorMessages());
            request.setIsValid(false);
            this.requestRepository.save(request);

            return this.gson.toJson(request.getErrorMessages());
        }
        request.setIsValid(true);
        this.requestRepository.save(request);
        this.jobProcessor.processJob(this.jobRepository.findById(jobId).get(), this.jobRepository);

        return "Job resumed";
    }
}
