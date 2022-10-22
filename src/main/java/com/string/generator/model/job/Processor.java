package com.string.generator.model.job;

import com.string.generator.adapter.FileWriterAdapter;
import com.string.generator.helper.UniqueValidator;
import org.hibernate.Hibernate;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Processor extends Thread
{
    private final Job job;
    private final JobRepository jobRepository;
    private final FileWriterAdapter fileWriterAdapter;
    private final UniqueValidator uniqueValidator;

    private String generatedString;

    public Processor(Job job, JobRepository jobRepository) throws IOException
    {
        String generatedFilePath = "C:\\Users\\duckiedot\\Desktop\\generated-string-" + job.getId() + ".txt";
        Hibernate.initialize(job.getGeneratedStrings());

        this.job = job;
        this.jobRepository = jobRepository;

        this.fileWriterAdapter = new FileWriterAdapter(generatedFilePath);
        this.uniqueValidator = new UniqueValidator(this.job);
    }

    @Override
    public void run()
    {
        job.startJob();
        this.jobRepository.save(job);

        /*
           This could be used in the future,
           if we need to add a case when program is stopped during generation, and we want to resume it
         */
        List<String> generatedElements = this.job.getGeneratedStrings();
        int generatedElementsCount = generatedElements != null ? generatedElements.size() : 0;

        for (int i = generatedElementsCount + 1; i < job.getExpectedResults(); i++) {
            this.generatedString = "";
            this.generatedString = this.generateUniqueString(new StringBuilder(job.getAllowedCharacters()));

            //If the string is already present in file, ignore it and regenerate it
            try {
                if (this.uniqueValidator.isUnique(this.generatedString)) {
                    this.writeStringToFile(this.generatedString);
                    this.job.appendGeneratedStrings(this.generatedString);
                    this.jobRepository.save(job);
                } else {
                    i--;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            this.fileWriterAdapter.closeFile();
            job.finishJob();
            this.jobRepository.save(job);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateUniqueString(StringBuilder allowedCharacters)
    {
        int charactersLeft = allowedCharacters.length();

        if (charactersLeft == 0) {
            //As per AC the generated string length can be anywhere >= minimum <= maximum
            int randomLengthInRange =
                    ThreadLocalRandom.current().nextInt(job.getMinimumLength(), job.getMaximumLength());

            return this.getSubstringForGivenLength(randomLengthInRange);
        }

        int randomCharIndex = (int) ((Math.random() * (charactersLeft - 1)) + 1);
        char randomChar = allowedCharacters.charAt(randomCharIndex-1);

        this.generatedString += randomChar;
        allowedCharacters.deleteCharAt(randomCharIndex-1);

        return this.generateUniqueString(allowedCharacters);
    }

    private void writeStringToFile(String generatedString) throws IOException
    {
        this.fileWriterAdapter.writeStringToFile(generatedString);
    }

    private String getSubstringForGivenLength(int length)
    {
        return this.generatedString.substring(0, length);
    }
}
