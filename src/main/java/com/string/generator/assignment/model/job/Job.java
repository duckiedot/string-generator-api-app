package com.string.generator.assignment.model.job;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;


@Entity
public class Job
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    public Date getStartedAt()
    {
        return startedAt;
    }

    public void setStartedAt(Date startedAt)
    {
        this.startedAt = startedAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getAllowedCharacters()
    {
        return allowedCharacters;
    }

    public void setAllowedCharacters(String allowedCharacters)
    {
        this.allowedCharacters = allowedCharacters;
    }

    public int getMinimumLength()
    {
        return minimumLength;
    }

    public void setMinimumLength(int minimumLength)
    {
        this.minimumLength = minimumLength;
    }

    public int getMaximumLength()
    {
        return maximumLength;
    }

    public void setMaximumLength(int maximumLength)
    {
        this.maximumLength = maximumLength;
    }

    public int getExpectedResults() {
        return expectedResults;
    }

    public void setExpectedResults(int expectedResults) {
        this.expectedResults = expectedResults;
    }
}
