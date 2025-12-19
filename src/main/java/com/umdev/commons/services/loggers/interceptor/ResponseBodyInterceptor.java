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

package com.umdev.commons.services.loggers.interceptor;

import com.umdev.commons.services.loggers.LoggingService;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/// ResponseBodyInterceptor. Intercepts the response body and logs it.
///
/// @author Luis Antonio Mata
/// @version 0.0.1
/// @since 21
@ControllerAdvice
public class ResponseBodyInterceptor implements ResponseBodyAdvice<Object> {
    private final LoggingService loggingService;

    /// Default constructor.
    /// @param loggingService the logging service
    /// @since 21
    public ResponseBodyInterceptor(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    /// Supports. Always returns true.
    /// @param methodParameter the method parameter
    /// @param aClass the class
    /// @return true
    /// @since 21
    /// @see ResponseBodyAdvice#supports(MethodParameter, Class)
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /// Before body write. Logs the response.
    /// @param body the body
    /// @param methodParameter the method parameter
    /// @param mediaType the media type
    /// @param aClass the class
    /// @param serverHttpRequest the server http request
    /// @param serverHttpResponse the server http response
    /// @return the body
    /// @since 21
    /// @see ResponseBodyAdvice#beforeBodyWrite(Object, MethodParameter, MediaType, Class, ServerHttpRequest, ServerHttpResponse)
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        loggingService.displayResponse(((ServletServerHttpRequest) serverHttpRequest).getServletRequest(),
                ((ServletServerHttpResponse) serverHttpResponse).getServletResponse(), body);
        return body;
    }
}
