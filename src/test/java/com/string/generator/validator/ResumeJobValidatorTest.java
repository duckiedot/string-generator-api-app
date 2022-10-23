package com.string.generator.validator;

import com.string.generator.model.job.Job;
import com.string.generator.model.job.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ResumeJobValidatorTest
{
    private final long JOB_ID = 5555;
    private ResumeJobValidator resumeJobValidator;
    private JobRepository jobRepositoryMock;
    private Job jobMock;

    @BeforeEach
    public void setup()
    {
        this.jobRepositoryMock = mock(JobRepository.class);
        this.jobMock = mock(Job.class);

        this.resumeJobValidator = new ResumeJobValidator(jobRepositoryMock);
    }

    @Test
    public void isValidTrue()
    {
        when(this.jobRepositoryMock.findById(this.JOB_ID)).thenReturn(Optional.of(this.jobMock));
        when(this.jobMock.getActive()).thenReturn((short)1);

        assertTrue(this.resumeJobValidator.isValid(this.JOB_ID));
    }

    @Test
    public void isValidFalse()
    {
        when(this.jobRepositoryMock.findById(this.JOB_ID)).thenReturn(Optional.of(this.jobMock));
        when(this.jobMock.getActive()).thenReturn((short)0);

        assertFalse(this.resumeJobValidator.isValid(this.JOB_ID));
    }
}