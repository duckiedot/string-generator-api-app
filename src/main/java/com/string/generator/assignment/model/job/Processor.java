package com.string.generator.assignment.model.job;

import com.string.generator.assignment.adapter.FileWriterAdapter;

import java.io.IOException;

public class Processor extends Thread
{
    private final Job job;
    private final JobRepository jobRepository;
    private final FileWriterAdapter fileWriterAdapter;

    private String generatedString;

    public Processor(Job job, JobRepository jobRepository) throws IOException {
        this.job = job;
        this.jobRepository = jobRepository;
        this.fileWriterAdapter =
                new FileWriterAdapter("C:\\Users\\duckiedot\\Desktop\\generated-string-" + job.getId() + ".txt");
    }

    @Override
    public void run()
    {
        job.startJob();
        this.jobRepository.save(job);

        for (int i = 1; i < job.getExpectedResults(); i++) {
            this.generatedString = "";
            this.generatedString = this.generateUniqueString(new StringBuilder(job.getAllowedCharacters()));

            try {
                this.writeStringToFile(this.generatedString);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            this.fileWriterAdapter.closeFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        job.finishJob();
        this.jobRepository.save(job);
    }

    private String generateUniqueString(StringBuilder allowedCharacters)
    {
        int charactersLeft = allowedCharacters.length();

        if (charactersLeft == 0) {
            //As per AC the generated string length can be anywhere >= minimum <= maximum
            int randomLengthInRange =
                    (int) ((Math.random() * job.getMaximumLength() - job.getMinimumLength()) + job.getMinimumLength());

            return this.getSubstringForGivenLength(randomLengthInRange);
        }

        int randomCharIndex = (int) ((Math.random() * (charactersLeft - 1)) + 1);
        char randomChar = allowedCharacters.charAt(randomCharIndex-1);

        this.generatedString += randomChar;
        allowedCharacters.deleteCharAt(randomCharIndex-1);

        return this.generateUniqueString(allowedCharacters);
    }

    private void writeStringToFile(String generatedString) throws IOException {
        this.fileWriterAdapter.writeStringToFile(generatedString);
    }

    private String getSubstringForGivenLength(int length)
    {
        return this.generatedString.substring(0, length);
    }
}
