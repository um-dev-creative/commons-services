package com.umdev.commons.services.loggers.interceptor;

import com.umdev.commons.services.loggers.LoggingService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;

import java.lang.reflect.Type;

class RequestBodyInterceptorTest {

    @Test
    @DisplayName("afterBodyRead logs non-null body and returns it")
    void afterBodyRead_withNonNullBody_logsAndReturnsBody() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        RequestBodyInterceptor interceptor = new RequestBodyInterceptor(loggingService, request);

        Object body = new Object();
        HttpInputMessage input = Mockito.mock(HttpInputMessage.class);

        Object returned = interceptor.afterBodyRead(body, input, null, (Type) Object.class, (Class<? extends HttpMessageConverter<?>>) (Object) Object.class);

        Mockito.verify(loggingService, Mockito.times(1)).displayRequest(request, body);
        Assertions.assertSame(body, returned);
    }

    @Test
    @DisplayName("afterBodyRead logs null body and returns null")
    void afterBodyRead_withNullBody_logsAndReturnsNull() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        RequestBodyInterceptor interceptor = new RequestBodyInterceptor(loggingService, request);

        HttpInputMessage input = Mockito.mock(HttpInputMessage.class);

        Object returned = interceptor.afterBodyRead(null, input, null, (Type) Object.class, (Class<? extends HttpMessageConverter<?>>) (Object) Object.class);

        Mockito.verify(loggingService, Mockito.times(1)).displayRequest(request, null);
        Assertions.assertNull(returned);
    }

    @Test
    @DisplayName("supports always returns true")
    void supports_alwaysReturnsTrue() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        RequestBodyInterceptor interceptor = new RequestBodyInterceptor(loggingService, request);

        boolean supported = interceptor.supports(null, (Type) Object.class, (Class<? extends HttpMessageConverter<?>>) (Object) Object.class);

        Assertions.assertTrue(supported);
    }

    @Test
    @DisplayName("afterBodyRead propagates exception from loggingService")
    void afterBodyRead_whenLoggingServiceThrows_propagatesException() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        RequestBodyInterceptor interceptor = new RequestBodyInterceptor(loggingService, request);

        Object body = "payload";
        HttpInputMessage input = Mockito.mock(HttpInputMessage.class);

        RuntimeException boom = new RuntimeException("boom");
        Mockito.doThrow(boom).when(loggingService).displayRequest(request, body);

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () ->
                interceptor.afterBodyRead(body, input, null, (Type) Object.class, (Class<? extends HttpMessageConverter<?>>) (Object) Object.class)
        );

        Assertions.assertSame(boom, thrown);
    }

    @Test
    @DisplayName("afterBodyRead with null HttpServletRequest still calls loggingService with null")
    void afterBodyRead_withNullHttpServletRequest_callsLoggingWithNull() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        RequestBodyInterceptor interceptor = new RequestBodyInterceptor(loggingService, null);

        Object body = 123;
        HttpInputMessage input = Mockito.mock(HttpInputMessage.class);

        Object returned = interceptor.afterBodyRead(body, input, null, (Type) Object.class, (Class<? extends HttpMessageConverter<?>>) (Object) Object.class);

        Mockito.verify(loggingService, Mockito.times(1)).displayRequest(null, body);
        Assertions.assertEquals(body, returned);
    }

    @Test
    @DisplayName("afterBodyRead with collection body logs and returns same instance")
    void afterBodyRead_withCollectionBody_logsAndReturnsSame() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        RequestBodyInterceptor interceptor = new RequestBodyInterceptor(loggingService, request);

        java.util.List<String> body = java.util.Arrays.asList("a", "b");
        HttpInputMessage input = Mockito.mock(HttpInputMessage.class);

        Object returned = interceptor.afterBodyRead(body, input, null, (Type) Object.class, (Class<? extends HttpMessageConverter<?>>) (Object) Object.class);

        Mockito.verify(loggingService, Mockito.times(1)).displayRequest(request, body);
        Assertions.assertSame(body, returned);
    }

    @Test
    @DisplayName("afterBodyRead with null LoggingService throws NullPointerException")
    void afterBodyRead_withNullLoggingService_throwsNullPointerException() {
        RequestBodyInterceptor interceptor = new RequestBodyInterceptor(null, Mockito.mock(HttpServletRequest.class));
        HttpInputMessage input = Mockito.mock(HttpInputMessage.class);

        Assertions.assertThrows(NullPointerException.class, () ->
                interceptor.afterBodyRead("payload", input, null, (Type) Object.class, (Class<? extends HttpMessageConverter<?>>) (Object) Object.class)
        );
    }
}
