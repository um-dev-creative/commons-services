package com.umdev.commons.services.loggers.interceptor;

import com.umdev.commons.services.loggers.LoggingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(value = {MockitoExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)
class ResponseBodyInterceptorTest {

    @Test
    @DisplayName("beforeBodyWrite with ServletServerHttpRequest/Response calls loggingService and returns the same body")
    void beforeBodyWrite_withServletRequests_callsLoggingAndReturnsBody() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        ResponseBodyInterceptor interceptor = new ResponseBodyInterceptor(loggingService);

        ServletServerHttpRequest servletReq = Mockito.mock(ServletServerHttpRequest.class);
        ServletServerHttpResponse servletResp = Mockito.mock(ServletServerHttpResponse.class);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito.when(servletReq.getServletRequest()).thenReturn(req);
        Mockito.when(servletResp.getServletResponse()).thenReturn(resp);

        Object body = new Object();

        Object returned = interceptor.beforeBodyWrite(body, null, null, (Class<? extends HttpMessageConverter<?>>) (Object) Object.class, servletReq, servletResp);

        Mockito.verify(loggingService).displayResponse(req, resp, body);
        Assertions.assertSame(body, returned);
    }

    @Test
    @DisplayName("beforeBodyWrite with null body still calls loggingService and returns null")
    void beforeBodyWrite_withNullBody_callsLoggingAndReturnsNull() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        ResponseBodyInterceptor interceptor = new ResponseBodyInterceptor(loggingService);

        ServletServerHttpRequest servletReq = Mockito.mock(ServletServerHttpRequest.class);
        ServletServerHttpResponse servletResp = Mockito.mock(ServletServerHttpResponse.class);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito.when(servletReq.getServletRequest()).thenReturn(req);
        Mockito.when(servletResp.getServletResponse()).thenReturn(resp);

        Object returned = interceptor.beforeBodyWrite(null, null, null, (Class<? extends HttpMessageConverter<?>>) (Object) Object.class, servletReq, servletResp);

        Mockito.verify(loggingService).displayResponse(req, resp, null);
        Assertions.assertNull(returned);
    }

    @Test
    @DisplayName("beforeBodyWrite with non-Servlet ServerHttpRequest throws ClassCastException")
    void beforeBodyWrite_withNonServletServerHttpRequest_throwsClassCastException() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        ResponseBodyInterceptor interceptor = new ResponseBodyInterceptor(loggingService);

        ServerHttpRequest nonServletReq = Mockito.mock(ServerHttpRequest.class);
        ServletServerHttpResponse servletResp = Mockito.mock(ServletServerHttpResponse.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito.when(servletResp.getServletResponse()).thenReturn(resp);

        Object body = "payload";

        Assertions.assertThrows(ClassCastException.class, () ->
                interceptor.beforeBodyWrite(body, null, null, (Class<? extends HttpMessageConverter<?>>) (Object) Object.class, nonServletReq, servletResp)
        );
    }

    @Test
    @DisplayName("beforeBodyWrite with non-Servlet ServerHttpResponse throws ClassCastException")
    void beforeBodyWrite_withNonServletServerHttpResponse_throwsClassCastException() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        ResponseBodyInterceptor interceptor = new ResponseBodyInterceptor(loggingService);

        ServletServerHttpRequest servletReq = Mockito.mock(ServletServerHttpRequest.class);
        ServerHttpResponse nonServletResp = Mockito.mock(ServerHttpResponse.class);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);

        Mockito.when(servletReq.getServletRequest()).thenReturn(req);

        Object body = "payload";

        Assertions.assertThrows(ClassCastException.class, () ->
                interceptor.beforeBodyWrite(body, null, null, (Class<? extends HttpMessageConverter<?>>) (Object) Object.class, servletReq, nonServletResp)
        );
    }

    @Test
    @DisplayName("supports always returns true")
    void supports_alwaysReturnsTrue() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        ResponseBodyInterceptor interceptor = new ResponseBodyInterceptor(loggingService);

        Assertions.assertTrue(interceptor.supports(null, (Class<? extends HttpMessageConverter<?>>) (Object) Object.class));
    }

    @Test
    @DisplayName("beforeBodyWrite propagates exception thrown by LoggingService")
    void beforeBodyWrite_whenLoggingServiceThrows_propagatesException() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        ResponseBodyInterceptor interceptor = new ResponseBodyInterceptor(loggingService);

        ServletServerHttpRequest servletReq = Mockito.mock(ServletServerHttpRequest.class);
        ServletServerHttpResponse servletResp = Mockito.mock(ServletServerHttpResponse.class);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito.when(servletReq.getServletRequest()).thenReturn(req);
        Mockito.when(servletResp.getServletResponse()).thenReturn(resp);

        RuntimeException ex = new RuntimeException("boom");
        Mockito.doThrow(ex).when(loggingService).displayResponse(req, resp, "payload");

        Assertions.assertSame(ex, Assertions.assertThrows(RuntimeException.class, () ->
                interceptor.beforeBodyWrite("payload", null, null, (Class<? extends HttpMessageConverter<?>>) (Object) Object.class, servletReq, servletResp)
        ));
    }
}

