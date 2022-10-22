package com.string.generator.assignment.model.job;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class Job
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private short active;

    @Column(nullable = false)
    private String allowedCharacters;

    @Column(nullable = false)
    private int minimumLength;

    @Column(nullable = false)
    private int maximumLength;

    @Column(nullable = false)
    private int expectedResults;

    @Column()
    private Date startedAt;

    @Column
    private Date finishedAt;

    @ElementCollection
    private List<String> generatedStrings;

    public void startJob()
    {
        this.setStartedAt(Date.from(Instant.now()));
    }

    public void finishJob()
    {
        this.setFinishedAt(Date.from(Instant.now()));
        this.setActive((short)0);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getActive() {
        return active;
    }

    public void setActive(short active) {
        this.active = active;
    }

    public String getAllowedCharacters() {
        return allowedCharacters;
    }

    public void setAllowedCharacters(String allowedCharacters) {
        this.allowedCharacters = allowedCharacters;
    }

    public int getMinimumLength() {
        return minimumLength;
    }

    public void setMinimumLength(int minimumLength) {
        this.minimumLength = minimumLength;
    }

    public int getMaximumLength() {
        return maximumLength;
    }

    public void setMaximumLength(int maximumLength) {
        this.maximumLength = maximumLength;
    }

    public int getExpectedResults() {
        return expectedResults;
    }

    public void setExpectedResults(int expectedResults) {
        this.expectedResults = expectedResults;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public List<String> getGeneratedStrings() {
        return generatedStrings;
    }

    public void appendGeneratedStrings(String generatedStrings) {
        if (this.getGeneratedStrings() == null) {
            this.generatedStrings = new ArrayList<String>();
        }
        this.generatedStrings.add(generatedStrings);
    }
}
