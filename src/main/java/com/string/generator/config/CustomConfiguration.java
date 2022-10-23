package com.string.generator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:/configuration.properties"),
        @PropertySource("classpath:/validator.properties")
})
public class CustomConfiguration {
}
