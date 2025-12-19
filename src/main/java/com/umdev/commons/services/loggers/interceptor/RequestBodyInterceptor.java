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

package com.umdev.commons.services.loggers.interceptor;

import com.umdev.commons.services.loggers.LoggingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

/// RequestBodyInterceptor. Intercepts the request body and logs it.
///
/// @author Luis Antonio Mata
/// @version 0.0.1
/// @since 21
@ControllerAdvice
public class RequestBodyInterceptor extends RequestBodyAdviceAdapter {

    private final LoggingService loggingService;

    private final HttpServletRequest request;

    ///  Constructor. Initializes the logging service and the request.
    /// @param loggingService the logging service
    /// @param request the request
    public RequestBodyInterceptor(LoggingService loggingService, HttpServletRequest request) {
        super();
        this.loggingService = loggingService;
        this.request = request;
    }

    ///  Intercepts the request body and logs it.
    /// @param body the body
    /// @param inputMessage the input message
    /// @param parameter the method parameter
    /// @param targetType the target type
    /// @param converterType the converter type
    /// @return the body
    /// @see RequestBodyAdviceAdapter#afterBodyRead(Object, HttpInputMessage, MethodParameter, Type, Class)
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        loggingService.displayRequest(request, body);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    ///  Supports. Always returns true.
    /// @param methodParameter the method parameter
    /// @param type the type
    /// @param aClass the class
    /// @return true
    /// @see RequestBodyAdviceAdapter#supports(MethodParameter, Type, Class)
    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }
}
