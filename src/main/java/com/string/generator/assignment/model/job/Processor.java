package com.string.generator.assignment.model.job;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;

public class Processor extends Thread
{
    private Job job;

    private JobRepository jobRepository;

    private File generatedFile;
    private FileWriter fileWriter;

    private String generatedString;

    public Processor(Job job, JobRepository jobRepository) throws IOException {
        this.job = job;
        this.jobRepository = jobRepository;
        this.generatedFile = new File(
                "C:\\Users\\duckiedot\\Desktop\\generated-string-" + job.getId() + ".txt"
        );
        this.fileWriter = new FileWriter(this.generatedFile, true);
    }

    @Override
    public void run()
    {
        job.setStartedAt(Date.from(Instant.now()));
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
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        job.setFinishedAt(Date.from(Instant.now()));
        job.setActive((short)0);
        this.jobRepository.save(job);
    }

    private String generateUniqueString(StringBuilder allowedCharacters)
    {
        int charactersLeft = allowedCharacters.length();

        if (charactersLeft == 0) {
            return this.generatedString;
        }

        int randomCharIndex = (int) ((Math.random() * (charactersLeft - 1)) + 1);
        char randomChar = allowedCharacters.charAt(randomCharIndex-1);

        this.generatedString += randomChar;
        allowedCharacters.deleteCharAt(randomCharIndex-1);

        return this.generateUniqueString(allowedCharacters);
    }

    private void writeStringToFile(String generatedString) throws IOException {
        this.fileWriter.write(generatedString);
        this.fileWriter.write('\n');
    }
}
