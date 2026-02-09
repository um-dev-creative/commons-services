package com.prx.commons.services.config.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Jackson.
 * Configures an {@link ObjectMapper} to support Java date/time types by registering the {@link JavaTimeModule}.
 */
@Configuration
public class JacksonConfig {

    /**
     * Default constructor.
     */
    public JacksonConfig() {
        // Default constructor
    }

    /**
     * Creates and configures an {@link ObjectMapper} bean.
     *
     * @return a configured ObjectMapper instance
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
