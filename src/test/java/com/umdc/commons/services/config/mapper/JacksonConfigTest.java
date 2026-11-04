package com.umdc.commons.services.config.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JacksonConfigTest {

    @Test
    @DisplayName("ObjectMapper is not null")
    void objectMapperIsNotNull() {
        JacksonConfig jacksonConfig = new JacksonConfig();
        ObjectMapper objectMapper = jacksonConfig.objectMapper();
        assertNotNull(objectMapper);
    }

    @Test
    @DisplayName("ObjectMapper round-trips java.time types")
    void objectMapperHandlesJavaTime() {
        ObjectMapper mapper = new JacksonConfig().objectMapper();
        LocalDate date = LocalDate.of(2026, 11, 3);
        LocalDateTime dateTime = LocalDateTime.of(2026, 11, 3, 10, 30, 15);

        assertEquals(date, mapper.readValue(mapper.writeValueAsString(date), LocalDate.class));
        assertEquals(dateTime, mapper.readValue(mapper.writeValueAsString(dateTime), LocalDateTime.class));
    }
}
