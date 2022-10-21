package com.string.generator.assignment.controller;

import com.google.gson.Gson;
import com.string.generator.assignment.model.job.Job;
import com.string.generator.assignment.model.job.JobRepository;
import com.string.generator.assignment.model.job.Validator;
import com.string.generator.assignment.model.processor.JobProcessor;
import com.string.generator.assignment.model.request.Request;
import com.string.generator.assignment.model.request.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    private Request request;

    @Autowired
    public CreateJob(Validator validator)
    {
        this.requestDataValidator = validator;
    }

    @PostMapping(value = "rest/createjob")
    public String createJob(@RequestBody Job job) throws IOException {
        request = new Request();
        if (!(this.requestDataValidator.isValid(job))) {
            request.setErrorMessages(this.requestDataValidator.getErrorMessages());
            request.setIsValid((short)0);
            this.requestRepository.save(request);

            return new Gson().toJson(this.request.getErrorMessages());
        }
        request.setIsValid((short)1);
        this.requestRepository.save(request);
        this.jobRepository.save(job);
        this.jobProcessor.processJob(job, this.jobRepository);

        return String.format(this.JOB_CREATED_MSG, job.getId());
    }
}
