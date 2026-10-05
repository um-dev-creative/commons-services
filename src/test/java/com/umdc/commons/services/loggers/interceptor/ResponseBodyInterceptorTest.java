package com.umdc.commons.services.loggers.interceptor;

import com.umdc.commons.services.loggers.LoggingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ResponseBodyInterceptorTest {

    @Test
    @DisplayName("beforeBodyWrite delegates to loggingService.displayResponse")
    void beforeBodyWrite_delegates_to_loggingService() {
        LoggingService loggingService = mock(LoggingService.class);
        ResponseBodyInterceptor interceptor = new ResponseBodyInterceptor(loggingService);

        ServletServerHttpRequest serverRequest = mock(ServletServerHttpRequest.class);
        ServletServerHttpResponse serverResponse = mock(ServletServerHttpResponse.class);

        when(serverRequest.getServletRequest()).thenReturn(null);
        when(serverResponse.getServletResponse()).thenReturn(null);

        Object result = interceptor.beforeBodyWrite("body", mock(MethodParameter.class), MediaType.APPLICATION_JSON,
                JacksonJsonHttpMessageConverter.class, serverRequest, serverResponse);

        // should return the original body
        assertEquals("body", result);
        verify(loggingService, times(1)).displayResponse(null, null, "body");
    }
}
