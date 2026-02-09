/*
 *  @(#)LogInterceptor.java
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
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Handler interceptor that logs incoming HTTP requests for supported HTTP methods.
 * It delegates request logging to the configured {@link LoggingService}.
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

    private final LoggingService loggingService;

    /**
     * Constructs a new LogInterceptor.
     *
     * @param loggingService the logging service used to record request information
     */
    public LogInterceptor(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    /**
     * Pre-handle logic executed before controller invocation. For GET, POST, DELETE and PUT
     * methods the interceptor will call {@link LoggingService#displayRequest}.
     *
     * @return true to continue processing the request
     */
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if (request.getMethod().equals(HttpMethod.GET.name())
                ||request.getMethod().equals(HttpMethod.POST.name())
                ||request.getMethod().equals(HttpMethod.DELETE.name())
                ||request.getMethod().equals(HttpMethod.PUT.name())) {
            loggingService.displayRequest(request, null);
        }
        return true;
    }
}
