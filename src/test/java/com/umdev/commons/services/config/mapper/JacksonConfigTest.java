package com.umdev.commons.services.config.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JacksonConfigTest {

    @Test
    @DisplayName("ObjectMapper is not null")
    void objectMapperIsNotNull() {
        JacksonConfig jacksonConfig = new JacksonConfig();
        ObjectMapper objectMapper = jacksonConfig.objectMapper();
        assertNotNull(objectMapper);
    }

}
