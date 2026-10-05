package com.umdc.commons.services.loggers;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class LoggingServiceImpUnitTest {

    @Test
    @DisplayName("displayRequest logs request when trace enabled and has parameters and body")
    void displayRequest_logs_with_parameters_and_body() {
        LoggingServiceImp service = new LoggingServiceImp();
        ReflectionTestUtils.setField(service, "isTraceEnabled", true);
        ListAppender<ILoggingEvent> appender = attachAppender();

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestURI()).thenReturn("/test");
        // parameter names enumeration
        when(request.getParameterNames()).thenReturn(Collections.enumeration(Collections.singletonList("p1")));
        when(request.getParameter("p1")).thenReturn("v1");

        assertDoesNotThrow(() -> service.displayRequest(request, "bodycontent"));
        List<String> messages = appender.list.stream().map(ILoggingEvent::getFormattedMessage).toList();
        assertAll(
                () -> assertFalse(messages.isEmpty()),
                () -> assertTrue(messages.stream().anyMatch(message -> message.contains("POST"))),
                () -> assertTrue(messages.stream().anyMatch(message -> message.contains("/test")))
        );
        verify(request).getMethod();
        verify(request).getRequestURI();
        verify(request).getParameterNames();
        verify(request).getParameter("p1");
    }

    @Test
    @DisplayName("displayRequest does nothing when trace disabled")
    void displayRequest_nops_when_trace_disabled() {
        LoggingServiceImp service = new LoggingServiceImp();
        ReflectionTestUtils.setField(service, "isTraceEnabled", false);
        ListAppender<ILoggingEvent> appender = attachAppender();

        HttpServletRequest request = mock(HttpServletRequest.class);

        assertDoesNotThrow(() -> service.displayRequest(request, null));
        assertTrue(appender.list.isEmpty());
        verifyNoInteractions(request);
    }

    @Test
    @DisplayName("displayResponse logs response when trace enabled and headers present")
    void displayResponse_logs_when_headers_present() {
        LoggingServiceImp service = new LoggingServiceImp();
        ReflectionTestUtils.setField(service, "isTraceEnabled", true);
        ListAppender<ILoggingEvent> appender = attachAppender();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getMethod()).thenReturn("GET");
        when(response.getHeaderNames()).thenReturn(Collections.singletonList("h1"));
        when(response.getHeader("h1")).thenReturn("v1");

        assertDoesNotThrow(() -> service.displayResponse(request, response, "resp"));
        List<String> messages = appender.list.stream().map(ILoggingEvent::getFormattedMessage).toList();
        assertAll(
                () -> assertFalse(messages.isEmpty()),
                () -> assertTrue(messages.stream().anyMatch(message -> message.contains("GET"))),
                () -> assertTrue(messages.stream().anyMatch(message -> message.contains("resp")))
        );
        verify(request).getMethod();
        verify(response).getHeaderNames();
        verify(response).getHeader("h1");
    }

    @Test
    @DisplayName("displayResponse does nothing when trace disabled")
    void displayResponse_nops_when_trace_disabled() {
        LoggingServiceImp service = new LoggingServiceImp();
        ReflectionTestUtils.setField(service, "isTraceEnabled", false);
        ListAppender<ILoggingEvent> appender = attachAppender();

        assertDoesNotThrow(() -> service.displayResponse(null, null, null));
        assertTrue(appender.list.isEmpty());
    }

    private ListAppender<ILoggingEvent> attachAppender() {
        Logger logger = (Logger) LoggerFactory.getLogger(LoggingServiceImp.class);
        logger.setLevel(Level.TRACE);

        ListAppender<ILoggingEvent> appender = new ListAppender<>();
        appender.start();
        logger.addAppender(appender);
        return appender;
    }
}

