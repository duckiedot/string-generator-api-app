package com.string.generator.validator;

import java.util.ArrayList;

public abstract class AbstractValidator
{
    protected ArrayList<String> errorMessages;

    public AbstractValidator()
    {
        this.errorMessages = new ArrayList<String>();
    }

    public ArrayList<String> getErrorMessages()
    {
        return this.errorMessages;
    }
}
