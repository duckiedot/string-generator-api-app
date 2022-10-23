package com.string.generator.helper;

import com.string.generator.model.job.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UniqueValidatorTest
{
    private final String GENERATED_STRING = "abcdfg";
    private UniqueValidator uniqueValidator;
    private Job jobMock;

    @BeforeEach
    public void setUp()
    {
        this.jobMock = mock(Job.class);
        this.uniqueValidator = new UniqueValidator(this.jobMock);
    }

    @Test
    public void isUniqueTrue()
    {
        when(this.jobMock.getGeneratedStrings()).thenReturn(new ArrayList<>());
        assertTrue(this.uniqueValidator.isUnique(this.GENERATED_STRING));
    }

    @Test
    public void isUniqueFalse()
    {
        List<String> generatedStrings = new ArrayList<>();
        generatedStrings.add(this.GENERATED_STRING);

        when(this.jobMock.getGeneratedStrings()).thenReturn(generatedStrings);
        assertFalse(this.uniqueValidator.isUnique(this.GENERATED_STRING));
    }
}