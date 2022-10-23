package com.string.generator.validator;

import com.string.generator.model.job.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ValidatorTest
{
    @Autowired
    private Validator validator;

    private Job jobMock;

    @BeforeEach
    public void setup()
    {
        this.jobMock = mock(Job.class);
    }

    @Test
    public void isValidTrue()
    {
        when(jobMock.getAllowedCharacters()).thenReturn("abcdef");
        when(jobMock.getMinimumLength()).thenReturn(4);
        when(jobMock.getMaximumLength()).thenReturn(5);
        when(jobMock.getExpectedResults()).thenReturn(25);

        boolean result = this.validator.isValid(jobMock);
        assertTrue(result);
    }

    @Test
    public void isValidFalse()
    {
        when(jobMock.getAllowedCharacters()).thenReturn("aabbcc");
        when(jobMock.getMinimumLength()).thenReturn(15);
        when(jobMock.getMaximumLength()).thenReturn(5);
        when(jobMock.getExpectedResults()).thenReturn(9999);

        boolean result = this.validator.isValid(jobMock);

        assertFalse(result);
        assertTrue(this.validator.getErrorMessages().size() > 0);
    }
}