package com.prx.commons.services.loggers.interceptor;

import com.prx.commons.services.loggers.LoggingService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.HttpInputMessage;

import java.lang.reflect.Type;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RequestBodyInterceptorTest {

    @Test
    @DisplayName("afterBodyRead should delegate to loggingService.displayRequest and return the same body")
    void afterBodyReadDelegatesAndReturnsBody() {
        LoggingService loggingService = mock(LoggingService.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        RequestBodyInterceptor interceptor = new RequestBodyInterceptor(loggingService, request);

        Object body = new Object();
        HttpInputMessage inputMessage = mock(HttpInputMessage.class);
        MethodParameter parameter = mock(MethodParameter.class);
        Type targetType = Object.class;
        Class<? extends HttpMessageConverter<?>> converterType = MappingJackson2HttpMessageConverter.class;

        Object result = interceptor.afterBodyRead(body, inputMessage, parameter, targetType, converterType);

        assertEquals(body, result);
        verify(loggingService, times(1)).displayRequest(eq(request), eq(body));
    }

    @Test
    @DisplayName("supports always returns true for any method parameter and type")
    void supportsAlwaysReturnsTrue() {
        LoggingService loggingService = mock(LoggingService.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        RequestBodyInterceptor interceptor = new RequestBodyInterceptor(loggingService, request);

        MethodParameter parameter = mock(MethodParameter.class);
        Type type = String.class;

        boolean supported = interceptor.supports(parameter, type, MappingJackson2HttpMessageConverter.class);

        assertTrue(supported);
    }
}
