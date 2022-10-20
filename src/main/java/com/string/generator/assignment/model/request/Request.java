package com.string.generator.assignment.model.request;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Request
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(nullable = false)
    private Date createdAt;

    @Column
    private short isValid;

    @ElementCollection
    private List<String> errorMessages;

    public short getIsValid()
    {
        return isValid;
    }

    public void setIsValid(short isValid)
    {
        this.isValid = isValid;
    }

    public List<String> getErrorMessages()
    {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages)
    {
        this.errorMessages = errorMessages;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date startedAt)
    {
        this.createdAt = startedAt;
    }
}
