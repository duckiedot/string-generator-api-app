package com.string.generator.model.job;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JobTest {

    private final Job job;

    public JobTest()
    {
        this.job = new Job();
    }

    @Test
    void finishJob() {
    }

    @Test
    void getId() {
        long id = 5555;
        this.job.setId(id);
        assertEquals(
                id,
                this.job.getId()
        );
    }

    @Test
    void getActive() {

        boolean active = true;
        boolean notActive = false;

        this.job.setActive(active);

        assertEquals(
                active,
                this.job.getActive()
        );

        this.job.setActive(notActive);

        assertEquals(
                notActive,
                this.job.getActive()
        );
    }

    @Test
    void getAllowedCharacters() {
        String allowedCharacters = "asdfghjklzxcvbnm";
        this.job.setAllowedCharacters(allowedCharacters);

        assertEquals(
                allowedCharacters,
                this.job.getAllowedCharacters()
        );
    }

    @Test
    void getMinimumLength() {
        int minimumLength = 5;
        this.job.setMinimumLength(minimumLength);

        assertEquals(
                minimumLength,
                this.job.getMinimumLength()
        );
    }

    @Test
    void getMaximumLength() {
        int maximumLength = 10;
        this.job.setMaximumLength(maximumLength);

        assertEquals(
                maximumLength,
                this.job.getMaximumLength()
        );
    }

    @Test
    void getExpectedResults() {
        int expectedResults = 10;
        this.job.setExpectedResults(expectedResults);

        assertEquals(
                expectedResults,
                this.job.getExpectedResults()
        );
    }

    @Test
    void getStartedAt() {
        Date date = Date.from(Instant.now());

        this.job.setStartedAt(date);

        assertEquals(
                date,
                this.job.getStartedAt()
        );

        assertNotEquals(
                Date.from(Instant.now()),
                this.job.getStartedAt()
        );
    }

    @Test
    void getFinishedAt() {
        this.job.finishJob();

        assertNotEquals(
                this.job.getStartedAt(),
                this.job.getFinishedAt()
        );
    }

    @Test
    void getGeneratedStrings() {
        String testString = "TestString";
        this.job.appendGeneratedStrings(testString);
        int size = this.job.getGeneratedStrings().size();
        assertEquals(1, size);
    }

}