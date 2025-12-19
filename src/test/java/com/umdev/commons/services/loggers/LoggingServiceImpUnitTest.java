package com.umdev.commons.services.loggers;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class LoggingServiceImpUnitTest {

    private LoggingServiceImp service;
    private TestAppender appender;
    private Logger rootLogger;

    static class TestAppender extends AppenderBase<ILoggingEvent> {
        final List<ILoggingEvent> events = new CopyOnWriteArrayList<>();

        @Override
        protected void append(ILoggingEvent eventObject) {
            events.add(eventObject);
        }
    }

    @BeforeEach
    void setUp() {
        service = new LoggingServiceImp();
        ReflectionTestUtils.setField(service, "isTraceEnabled", true);

        rootLogger = (Logger) LoggerFactory.getLogger(LoggingServiceImp.class);
        appender = new TestAppender();
        appender.setContext(rootLogger.getLoggerContext());
        appender.start();
        rootLogger.addAppender(appender);
        rootLogger.setLevel(Level.INFO);
    }

    @AfterEach
    void tearDown() {
        rootLogger.detachAppender(appender);
        appender.stop();
    }

    @Test
    @DisplayName("displayRequest logs method path parameters and body when present")
    void displayRequest_withParametersAndBody_logsAllParts() {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getMethod()).thenReturn("GET");
        Mockito.when(req.getRequestURI()).thenReturn("/api/test");

        Enumeration<String> names = Collections.enumeration(Arrays.asList("p1", "p2"));
        Mockito.when(req.getParameterNames()).thenReturn(names);
        Mockito.when(req.getParameter("p1")).thenReturn("v1");
        Mockito.when(req.getParameter("p2")).thenReturn("v2");

        Object body = "payload";

        service.displayRequest(req, body);

        Assertions.assertFalse(appender.events.isEmpty(), "Expected a log event");
        String msg = appender.events.get(0).getFormattedMessage();
        Assertions.assertTrue(msg.contains("REQUEST method = [GET]"));
        Assertions.assertTrue(msg.contains("path = [/api/test]"));
        Assertions.assertTrue(msg.contains("parameters"));
        Assertions.assertTrue(msg.contains("payload"));
    }

    @Test
    @DisplayName("displayRequest with no parameters and null body still logs method and path")
    void displayRequest_withNoParametersAndNullBody_logsMethodAndPath() {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getMethod()).thenReturn("POST");
        Mockito.when(req.getRequestURI()).thenReturn("/submit");

        Mockito.when(req.getParameterNames()).thenReturn(Collections.emptyEnumeration());

        service.displayRequest(req, null);

        Assertions.assertFalse(appender.events.isEmpty());
        String msg = appender.events.get(0).getFormattedMessage();
        Assertions.assertTrue(msg.contains("REQUEST method = [POST]"));
        Assertions.assertTrue(msg.contains("path = [/submit]"));
        Assertions.assertFalse(msg.contains("parameters = ["));
    }

    @Test
    @DisplayName("displayResponse logs headers and body when present")
    void displayResponse_withHeadersAndBody_logsHeadersAndBody() {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito.when(req.getMethod()).thenReturn("GET");
        Mockito.when(resp.getHeaderNames()).thenReturn(Arrays.asList("h1", "h2"));
        Mockito.when(resp.getHeader("h1")).thenReturn("val1");
        Mockito.when(resp.getHeader("h2")).thenReturn("val2");

        Object body = 12345;

        service.displayResponse(req, resp, body);

        Assertions.assertFalse(appender.events.isEmpty());
        String msg = appender.events.get(0).getFormattedMessage();
        Assertions.assertTrue(msg.contains("RESPONSE method = [GET]"));
        Assertions.assertTrue(msg.contains("ResponseHeaders"));
        Assertions.assertTrue(msg.contains("val1"));
        Assertions.assertTrue(msg.contains("responseBody = [12345]"));
    }

    @Test
    @DisplayName("displayResponse with null response throws NullPointerException")
    void displayResponse_withNullResponse_throwsNPE() {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getMethod()).thenReturn("GET");

        Assertions.assertThrows(NullPointerException.class, () -> service.displayResponse(req, null, "x"));
    }

    @Test
    @DisplayName("displayRequest with null request throws NullPointerException")
    void displayRequest_withNullRequest_throwsNPE() {
        Assertions.assertThrows(NullPointerException.class, () -> service.displayRequest(null, "b"));
    }

    @Test
    @DisplayName("when trace disabled no logs are emitted")
    void noLoggingWhenTraceDisabled() {
        ReflectionTestUtils.setField(service, "isTraceEnabled", false);

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getMethod()).thenReturn("GET");
        Mockito.when(req.getRequestURI()).thenReturn("/no");
        Mockito.when(req.getParameterNames()).thenReturn(Collections.emptyEnumeration());

        service.displayRequest(req, "b");
        service.displayResponse(req, Mockito.mock(HttpServletResponse.class), "b");

        Assertions.assertTrue(appender.events.isEmpty(), "No events should be logged when trace disabled");
    }
}

