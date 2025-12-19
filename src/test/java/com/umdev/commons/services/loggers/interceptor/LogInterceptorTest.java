package com.umdev.commons.services.loggers.interceptor;

import com.umdev.commons.services.loggers.LoggingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;

class LogInterceptorTest {

    @Test
    @DisplayName("preHandle with GET method calls displayRequest and returns true")
    void preHandle_withGET_callsDisplayRequestAndReturnsTrue() throws Exception {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        LogInterceptor interceptor = new LogInterceptor(loggingService);

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        Object handler = new Object();

        Mockito.when(req.getMethod()).thenReturn(HttpMethod.GET.name());

        boolean result = interceptor.preHandle(req, resp, handler);

        Mockito.verify(loggingService, Mockito.times(1)).displayRequest(req, null);
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("preHandle with HEAD method does not call displayRequest and returns true")
    void preHandle_withHEAD_doesNotCallDisplayRequestAndReturnsTrue() throws Exception {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        LogInterceptor interceptor = new LogInterceptor(loggingService);

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        Object handler = new Object();

        Mockito.when(req.getMethod()).thenReturn("HEAD");

        boolean result = interceptor.preHandle(req, resp, handler);

        Mockito.verify(loggingService, Mockito.never()).displayRequest(Mockito.any(), Mockito.any());
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("preHandle with lowercase get does not call displayRequest (case-sensitive) and returns true")
    void preHandle_withLowercaseGet_doesNotCallDisplayRequest() throws Exception {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        LogInterceptor interceptor = new LogInterceptor(loggingService);

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        Object handler = new Object();

        Mockito.when(req.getMethod()).thenReturn("get");

        boolean result = interceptor.preHandle(req, resp, handler);

        Mockito.verify(loggingService, Mockito.never()).displayRequest(Mockito.any(), Mockito.any());
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("preHandle propagates exception thrown by LoggingService")
    void preHandle_whenLoggingServiceThrows_propagatesException() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        LogInterceptor interceptor = new LogInterceptor(loggingService);

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        Object handler = new Object();

        Mockito.when(req.getMethod()).thenReturn(HttpMethod.POST.name());
        RuntimeException boom = new RuntimeException("boom");
        Mockito.doThrow(boom).when(loggingService).displayRequest(req, null);

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () ->
                interceptor.preHandle(req, resp, handler)
        );

        Assertions.assertSame(boom, thrown);
    }

    @Test
    @DisplayName("preHandle with null method throws NullPointerException")
    void preHandle_withNullMethod_throwsNullPointerException() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        LogInterceptor interceptor = new LogInterceptor(loggingService);

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        Object handler = new Object();

        Mockito.when(req.getMethod()).thenReturn(null);

        Assertions.assertThrows(NullPointerException.class, () -> interceptor.preHandle(req, resp, handler));
    }

    @Test
    @DisplayName("preHandle with null request throws NullPointerException")
    void preHandle_withNullRequest_throwsNullPointerException() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        LogInterceptor interceptor = new LogInterceptor(loggingService);

        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        Object handler = new Object();

        Assertions.assertThrows(NullPointerException.class, () -> interceptor.preHandle(null, resp, handler));
    }
}

