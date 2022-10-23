package com.string.generator.validator;

import com.string.generator.model.job.Job;
import com.string.generator.model.job.JobRepository;
import com.string.generator.service.ConfigurationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;

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
    private ConfigurableEnvironment configMock;

    @BeforeEach
    public void setup()
    {
        this.jobRepositoryMock = mock(JobRepository.class);
        this.jobMock = mock(Job.class);
        ConfigurationService service = mock(ConfigurationService.class);
        this.configMock = mock(ConfigurableEnvironment.class);
        when(service.properties()).thenReturn(this.configMock);

        this.resumeJobValidator = new ResumeJobValidator(jobRepositoryMock, service);
    }

    @Test
    public void isValidTrue()
    {
        when(this.jobRepositoryMock.findById(this.JOB_ID)).thenReturn(Optional.of(this.jobMock));
        when(this.jobMock.getActive()).thenReturn(true);

        assertTrue(this.resumeJobValidator.isValid(this.JOB_ID));
    }

    @Test
    public void isValidFalse()
    {
        when(this.jobRepositoryMock.findById(this.JOB_ID)).thenReturn(Optional.of(this.jobMock));
        when(this.jobMock.getActive()).thenReturn(false);
        when(this.configMock.getProperty("resume.job.invalid.id"))
                .thenReturn("Job with requested ID either doesn't exist, or is already finished");

        assertFalse(this.resumeJobValidator.isValid(this.JOB_ID));
    }
}