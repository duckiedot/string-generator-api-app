package com.string.generator.assignment.controller;

import com.string.generator.assignment.model.job.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class CreateJob
{
    private Validator requestDataValidator;

    public CreateJob()
    {
        this.requestDataValidator = new Validator();
    }

    @RequestMapping(value = "rest/jobmanager/create", method = POST)
    @ResponseBody
    public String createJob()
    {
        return "test";
    }
}
