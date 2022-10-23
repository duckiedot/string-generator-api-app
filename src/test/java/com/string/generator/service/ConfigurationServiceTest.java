package com.string.generator.service;

import com.string.generator.config.CustomConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConfigurationServiceTest {

    @Autowired
    ConfigurationService customConfiguration;

    @Test
    public void properties()
    {
        assertEquals(
                "C:/Users/duckiedot/Desktop/generated-string-",
                customConfiguration.properties().getProperty("path")
        );
    }
}