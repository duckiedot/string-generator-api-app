package com.string.generator.service;

import com.string.generator.config.CustomConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class ConfigurationService
{
    private AnnotationConfigApplicationContext context = null;

    public ConfigurableEnvironment properties()
    {
        if (context == null) {
            context = new AnnotationConfigApplicationContext();
            context.register(CustomConfiguration.class);
            context.refresh();
        }

        return context.getEnvironment();
    }
}
