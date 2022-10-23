package com.string.generator.validator;

import com.string.generator.model.job.Job;
import com.string.generator.model.job.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class DownloadValidatorTest
{
    private final long JOB_ID = 5555;

    private final short JOB_STATUS_ACTIVE = 1;
    private final short JOB_STATUS_NOT_ACTIVE = 0;

    private DownloadValidator downloadValidator;
    private JobRepository jobRepositoryMock;
    private Job jobMock;

    @BeforeEach
    public void setUp()
    {
        this.jobMock = mock(Job.class);
        this.jobRepositoryMock = mock(JobRepository.class);
        this.downloadValidator = new DownloadValidator(this.jobRepositoryMock);
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
        when(this.jobRepositoryMock.findById(this.JOB_ID)).thenReturn(Optional.of(this.jobMock));
        when(this.jobMock.getActive()).thenReturn(this.JOB_STATUS_ACTIVE);

        assertFalse(this.downloadValidator.isValid(this.JOB_ID));
    }
}