/*
 *  @(#)RequestBodyInterceptor.java
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
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

/**
 * Intercepts request bodies and logs them using the provided {@link LoggingService}.
 */
@ControllerAdvice
public class RequestBodyInterceptor extends RequestBodyAdviceAdapter {

    private final LoggingService loggingService;

    private final HttpServletRequest request;

    /**
     * Creates a new RequestBodyInterceptor.
     *
     * @param loggingService the logging service used to log the request body
     * @param request        the current HTTP servlet request
     */
    public RequestBodyInterceptor(LoggingService loggingService, HttpServletRequest request) {
        super();
        this.loggingService = loggingService;
        this.request = request;
    }

    /**
     * Called after the request body is read. Delegates logging to {@link LoggingService#displayRequest}.
     */
    @Override
    public @NonNull Object afterBodyRead(@NonNull Object body, @NonNull HttpInputMessage inputMessage,
                                @NonNull MethodParameter parameter, @NonNull Type targetType,
                                @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        loggingService.displayRequest(request, body);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    /**
     * Supports all controller methods.
     */
    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Type type,
                            @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }
}
