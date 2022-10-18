package com.string.generator.assignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class CreateJob
{
    @RequestMapping(value = "rest/jobmanager/create", method = POST)
    @ResponseBody
    public String createJob()
    {
        return "test";
    }
}
