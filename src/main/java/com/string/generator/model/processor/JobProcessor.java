package com.string.generator.model.processor;

import com.string.generator.model.job.Job;
import com.string.generator.model.job.JobRepository;
import com.string.generator.model.job.Processor;
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
