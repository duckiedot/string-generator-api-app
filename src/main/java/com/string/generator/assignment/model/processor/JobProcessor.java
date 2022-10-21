package com.string.generator.assignment.model.processor;

import com.string.generator.assignment.model.job.Job;
import com.string.generator.assignment.model.job.JobRepository;
import com.string.generator.assignment.model.job.Processor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JobProcessor
{
    public void processJob(Job job, JobRepository jobRepository) throws IOException {
        Processor processor = new Processor(job, jobRepository);
        processor.start();
    }
}
