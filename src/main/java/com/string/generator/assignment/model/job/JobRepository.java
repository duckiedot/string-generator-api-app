package com.string.generator.assignment.model.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JobRepository
{
    private final List<Job> jobs;
    private final JobFactory jobFactory;

    public JobRepository()
    {
        this.jobs = Collections.synchronizedList(new ArrayList<Job>());
        this.jobFactory = new JobFactory();
    }

    /**
     *
     * @return all jobs stored
     */
    public List<Job> getAll()
    {
        return this.jobs;
    }

    /**
     *
     * @param jobId id of the job we want to get
     * @return Job object with given id
     */
    public Optional<Job> getJobById(int jobId)
    {
        return this.jobs.stream().filter(job -> job.getId() == jobId).findFirst();
    }

    /**
     *
     * @return newly created Job object
     */
    public Job create()
    {
        Job newJob = this.jobFactory.create();
        this.jobs.add(newJob);

        return newJob;
    }
}
