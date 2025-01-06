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

/// LoggingService.
///
/// @author Luis Antonio Mata
/// @version 0.0.1
/// @since 21
public interface LoggingService {

    ///  Display the request.
    /// @param httpServletRequest the httpServletRequest
    /// @param body the body
    void displayRequest(HttpServletRequest httpServletRequest, Object body);

    ///  Display the response.
    /// @param httpServletRequest the httpServletRequest
    /// @param httpServletResponse the httpServletResponse
    /// @param body the body
    void displayResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body);
}
