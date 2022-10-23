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
class DownloadValidatorTest
{
    private final long JOB_ID = 5555;

    private final boolean JOB_STATUS_ACTIVE = true;
    private final boolean JOB_STATUS_NOT_ACTIVE = false;

    private DownloadValidator downloadValidator;
    private JobRepository jobRepositoryMock;
    private Job jobMock;

    private ConfigurableEnvironment configMock;

    @BeforeEach
    public void setUp()
    {
        this.jobMock = mock(Job.class);
        this.jobRepositoryMock = mock(JobRepository.class);
        ConfigurationService service = mock(ConfigurationService.class);
        this.configMock = mock(ConfigurableEnvironment.class);
        when(service.properties()).thenReturn(this.configMock);
        this.downloadValidator = new DownloadValidator(this.jobRepositoryMock, service);
    }

    @Test
    public void isValidTrue()
    {
        when(this.jobRepositoryMock.findById(this.JOB_ID)).thenReturn(Optional.of(this.jobMock));
        when(this.jobMock.getActive()).thenReturn(this.JOB_STATUS_NOT_ACTIVE);

        assertTrue(this.downloadValidator.isValid(this.JOB_ID));
    }

    @Test
    public void isValidFalse()
    {
        when(this.configMock.getProperty("download.job.not.finished"))
                .thenReturn("Job with given ID is not finished");
        when(this.jobRepositoryMock.findById(this.JOB_ID)).thenReturn(Optional.of(this.jobMock));
        when(this.jobMock.getActive()).thenReturn(this.JOB_STATUS_ACTIVE);

        assertFalse(this.downloadValidator.isValid(this.JOB_ID));
    }
}