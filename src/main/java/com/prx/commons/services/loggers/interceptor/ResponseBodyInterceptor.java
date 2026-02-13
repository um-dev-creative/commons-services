/*
 *  @(#)ResponseBodyInterceptor.java
 *
 *  Copyright (c) Luis Antonio Mata Mata. All rights reserved.
 *
 *   All rights to this product are owned by Luis Antonio Mata Mata and may only
 *  be used under the terms of its associated license document. You may NOT
 *  copy, modify, sublicense, or distribute this source file or portions of
 *  it unless previously authorized in writing by Luis Antonio Mata Mata.
 *  In any event, this notice and the above copyright must always be included
 *  verbatim with this file.
 */

package com.prx.commons.services.loggers.interceptor;

import com.prx.commons.services.loggers.LoggingService;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Intercepts controller responses and logs response details using {@link LoggingService}.
 */
@ControllerAdvice
public class ResponseBodyInterceptor implements ResponseBodyAdvice<Object> {
    private final LoggingService loggingService;

    /**
     * Constructs a new ResponseBodyInterceptor.
     *
     * @param loggingService the logging service used to record response information
     */
    public ResponseBodyInterceptor(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    /**
     * Supports all response types.
     */
    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * Called before the body is written to the response. It delegates logging to
     * {@link LoggingService#displayResponse}.
     */
    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter methodParameter, @NonNull MediaType mediaType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> aClass, @NonNull ServerHttpRequest serverHttpRequest,
                                  @NonNull ServerHttpResponse serverHttpResponse) {
        loggingService.displayResponse(((ServletServerHttpRequest) serverHttpRequest).getServletRequest(),
                ((ServletServerHttpResponse) serverHttpResponse).getServletResponse(), body);
        return body;
    }
}
