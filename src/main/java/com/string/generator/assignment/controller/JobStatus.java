package com.string.generator.assignment.controller;

import com.google.gson.Gson;
import com.string.generator.assignment.model.job.Job;
import com.string.generator.assignment.model.job.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class JobStatus
{
    private final short JOB_RUNNING_CODE = 1;
    private final String MSG_JOBS_RUNNING = "Currently there are %o jobs running";

    @Autowired
    private JobRepository jobsRepository;

    @Autowired
    private Gson gson;

    @GetMapping(value = "rest/jobs/get-all-active")
    @ResponseBody
    public String getJobsRunning()
    {
        return this.gson.toJson(this.jobsRepository.findByActive(this.JOB_RUNNING_CODE));
    }

    @GetMapping(value = "rest/jobs/get-active-count")
    @ResponseBody
    public String getJobsRunningCount()
    {
        return this.gson.toJson(
                String.format(this.MSG_JOBS_RUNNING, this.jobsRepository.findAll().size())
        );
    }

    @GetMapping(value = "rest/jobs/get-by-id/{jobId}")
    @ResponseBody
    public Optional<Job> getJobById(@PathVariable long jobId)
    {
        return this.jobsRepository.findById(jobId);
    }
    @GetMapping(value = "rest/jobs/get-all-jobs")
    @ResponseBody
    public String getAllJobs()
    {
        return this.gson.toJson(this.jobsRepository.findAll());
    }
}
