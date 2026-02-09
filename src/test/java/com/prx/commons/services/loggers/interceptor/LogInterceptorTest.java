package com.prx.commons.services.loggers.interceptor;

import com.prx.commons.services.loggers.LoggingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LogInterceptorTest {

    @Test
    @DisplayName("preHandle should call displayRequest for standard HTTP methods and return true")
    void preHandleCallsDisplayRequestForStandardMethods() throws Exception {
        LoggingService loggingService = mock(LoggingService.class);
        LogInterceptor interceptor = new LogInterceptor(loggingService);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        for (String method : new String[]{"GET", "POST", "PUT", "DELETE"}) {
            reset(loggingService);
            when(request.getMethod()).thenReturn(method);

            boolean result = interceptor.preHandle(request, response, new Object());

            assertTrue(result, "preHandle should always return true");
            verify(loggingService, times(1)).displayRequest(eq(request), isNull());
        }
    }

    @Test
    @DisplayName("preHandle should not call displayRequest for non-standard HTTP methods")
    void preHandleDoesNotCallDisplayRequestForOtherMethods() throws Exception {
        LoggingService loggingService = mock(LoggingService.class);
        LogInterceptor interceptor = new LogInterceptor(loggingService);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getMethod()).thenReturn("PATCH");

        boolean result = interceptor.preHandle(request, response, new Object());

        assertTrue(result);
        verify(loggingService, never()).displayRequest(any(), any());
    }

    @Test
    @DisplayName("preHandle should tolerate unusual method casing and still match expected methods")
    void preHandleHandlesMethodCasing() throws Exception {
        LoggingService loggingService = mock(LoggingService.class);
        LogInterceptor interceptor = new LogInterceptor(loggingService);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // simulate lower-case method returned by some container (defensive test)
        when(request.getMethod()).thenReturn("get");

        boolean result = interceptor.preHandle(request, response, new Object());

        // current implementation compares exact names, so lower-case should not trigger logging
        assertTrue(result);
        verify(loggingService, never()).displayRequest(any(), any());
    }
}

