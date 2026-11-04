package com.umdc.commons.services.config.mapper;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Jackson.
 * Configures a Jackson 3.x {@link ObjectMapper} (built via {@link JsonMapper}); Java date/time types
 * are supported natively by jackson-databind 3.x, so no extra module registration is required.
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
        return JsonMapper.builder().build();
    }
}
