/*
 *  @(#)LoggingService.java
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

package com.prx.commons.services.loggers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Logging service contract for logging HTTP requests and responses.
 * Implementations may log request and response details depending on configuration.
 *
 * @since 21
 */
public interface LoggingService {

    /**
     * Display the HTTP request information.
     *
     * @param httpServletRequest the HTTP servlet request
     * @param body               the request body (may be null)
     */
    void displayRequest(HttpServletRequest httpServletRequest, Object body);

    /**
     * Display the HTTP response information.
     *
     * @param httpServletRequest  the HTTP servlet request
     * @param httpServletResponse the HTTP servlet response
     * @param body                the response body (may be null)
     */
    void displayResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body);
}
