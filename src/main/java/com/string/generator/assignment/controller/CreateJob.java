package com.string.generator.assignment.controller;

import com.google.gson.Gson;
import com.string.generator.assignment.model.job.Job;
import com.string.generator.assignment.model.job.JobRepository;
import com.string.generator.assignment.validator.ResumeJobValidator;
import com.string.generator.assignment.validator.Validator;
import com.string.generator.assignment.model.processor.JobProcessor;
import com.string.generator.assignment.model.request.Request;
import com.string.generator.assignment.model.request.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
public class CreateJob
{
    private final String JOB_CREATED_MSG = "Job created successfully with ID: %o";

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobProcessor jobProcessor;

    private final Validator requestDataValidator;
    private final ResumeJobValidator resumeJobValidator;

    @Autowired
    public CreateJob(Validator validator, ResumeJobValidator resumeJobValidator)
    {
        this.requestDataValidator = validator;
        this.resumeJobValidator = resumeJobValidator;
    }

    @PostMapping(value = "rest/createjob")
    public String createJob(@RequestBody Job job) throws IOException {
        Request request = new Request();
        if (!(this.requestDataValidator.isValid(job))) {
            request.setErrorMessages(this.requestDataValidator.getErrorMessages());
            request.setIsValid((short)0);
            this.requestRepository.save(request);

            return new Gson().toJson(request.getErrorMessages());
        }
        request.setIsValid((short)1);
        this.requestRepository.save(request);
        this.jobRepository.save(job);
        this.jobProcessor.processJob(job, this.jobRepository);

        return String.format(this.JOB_CREATED_MSG, job.getId());
    }

    @PostMapping(value = "rest/resumejob/")
    public String resumeJob(@RequestBody long jobId) throws IOException {
        Request request = new Request();

        if (!this.resumeJobValidator.isValid(jobId)) {
            request.setErrorMessages(this.resumeJobValidator.getErrorMessages());
            request.setIsValid((short)0);
            this.requestRepository.save(request);

            return new Gson().toJson(request.getErrorMessages());
        }
        request.setIsValid((short)1);
        this.requestRepository.save(request);
        this.jobProcessor.processJob(this.jobRepository.findById(jobId).get(), this.jobRepository);

        return "Job resumed";
    }
}
