package com.string.generator.controller;

import com.google.gson.Gson;
import com.string.generator.model.request.Request;
import com.string.generator.model.request.RequestRepository;
import com.string.generator.service.ConfigurationService;
import com.string.generator.validator.DownloadValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class Download
{
    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private DownloadValidator downloadValidator;

    @RequestMapping(path = "rest/download-by-id", method = RequestMethod.GET)
    public ResponseEntity<Object> download(@RequestParam("id") long id) throws IOException
    {
        Request request = new Request();

        if (!this.downloadValidator.isValid(id)) {
            return ResponseEntity.badRequest().body(this.downloadValidator.getErrorMessages());
        }

        File file = new File(this.configurationService.properties().getProperty("path") + id + ".txt");

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=result.txt");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

}
