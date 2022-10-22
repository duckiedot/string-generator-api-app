package com.string.generator.helper;

import com.string.generator.model.job.Job;

import java.util.*;

public class UniqueValidator
{
    private final Job job;

    public UniqueValidator(Job currentJob)
    {
        this.job = currentJob;
    }

    public boolean isUnique(String generatedString)
    {
        List<String> alreadyGeneratedStrings = this.job.getGeneratedStrings();

        return (alreadyGeneratedStrings == null || alreadyGeneratedStrings
               .stream().noneMatch(s -> s.equals(generatedString)));
    }
}
