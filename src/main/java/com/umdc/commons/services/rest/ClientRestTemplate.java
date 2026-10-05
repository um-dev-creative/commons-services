/*
 *  @(#)ClientRestTemplate.java
 *
 *  Copyright (c) Luis Antonio Mata Mata. All rights reserved.
 *
 *  All rights to this product are owned by Luis Antonio Mata Mata and may only
 *  be used under the terms of its associated license document. You may NOT
 *  copy, modify, sublicense, or distribute this source file or portions of
 *  it unless previously authorized in writing by Luis Antonio Mata Mata.
 *  In any event, this notice and the above copyright must always be included
 *  verbatim with this file.
 */

package com.umdc.commons.services.rest;

import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * RestTemplate wrapper that configures a {@link RestTemplate} with a provided
 * {@link MappingJackson2HttpMessageConverter} and default settings (buffering, message converters).
 *
 * <p>Uses {@link BufferingClientHttpRequestFactory} so that request/response bodies can be
 * read multiple times by the logging interceptors.
 *
 * @since 21
 * @version 0.0.1
 */
@SuppressWarnings({"deprecation", "removal"})
public class ClientRestTemplate {

    protected final MappingJackson2HttpMessageConverter messageConverter;
    protected RestTemplate restTemplate;

    /**
     * Constructs the client with the provided Jackson message converter and initializes the RestTemplate.
     *
     * @param messageConverter the Jackson 2.x message converter to use
     */
    public ClientRestTemplate(MappingJackson2HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
        init();
    }

    /**
     * Initialize the RestTemplate with default interceptors and message converters.
     */
    protected final void init() {
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(APPLICATION_JSON);
        messageConverter.setSupportedMediaTypes(supportedMediaTypes);

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(messageConverter);

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

        // initialize the rest template
        restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        restTemplate.setInterceptors(interceptors);
        restTemplate.setMessageConverters(messageConverters);
    }
}
