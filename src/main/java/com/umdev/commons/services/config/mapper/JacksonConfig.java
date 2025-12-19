package com.umdev.commons.services.config.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/// Configuration class for Jackson.
/// This class configures the Jackson [ObjectMapper] to support Java 8 date and time API.
@Configuration
public class JacksonConfig {

    /// Default constructor.
    public JacksonConfig() {
        // Default constructor
    }

    /// Creates and configures an [ObjectMapper] bean.
    /// The [ObjectMapper] is configured to support Java 8 date and time API by registering the [JavaTimeModule].
    ///
    /// @return a configured [ObjectMapper] instance
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
