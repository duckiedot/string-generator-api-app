package com.string.generator.assignment.model.job;

public class JobFactory
{
    private static int totalJobsCreated;

    public Job create()
    {
        totalJobsCreated++;
        return new Job(totalJobsCreated);
    }
}
