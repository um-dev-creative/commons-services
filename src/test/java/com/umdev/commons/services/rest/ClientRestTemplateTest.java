package com.umdev.commons.services.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientRestTemplateTest {

    @Test
    @DisplayName("Initialization with valid message converter")
    void initializationWithValidMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ClientRestTemplate clientRestTemplate = new ClientRestTemplate(converter);

        assertNotNull(clientRestTemplate.restTemplate);
        List<MediaType> supportedMediaTypes = converter.getSupportedMediaTypes();
        assertTrue(supportedMediaTypes.contains(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Initialization with null message converter")
    void initializationWithNullMessageConverter() {
        assertThrows(NullPointerException.class, () -> new ClientRestTemplate(null));
    }

    @Test
    @DisplayName("RestTemplate has correct message converters")
    void restTemplateHasCorrectMessageConverters() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ClientRestTemplate clientRestTemplate = new ClientRestTemplate(converter);

        assertTrue(clientRestTemplate.restTemplate.getMessageConverters().contains(converter));
    }

    @Test
    @DisplayName("RestTemplate has no interceptors by default")
    void restTemplateHasNoInterceptorsByDefault() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ClientRestTemplate clientRestTemplate = new ClientRestTemplate(converter);

        assertTrue(clientRestTemplate.restTemplate.getInterceptors().isEmpty());
    }
}
