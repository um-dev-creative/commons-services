package com.prx.commons.services.loggers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.mockito.Mockito.*;

class LoggingServiceImpUnitTest {

    @Test
    @DisplayName("displayRequest logs request when trace enabled and has parameters and body")
    void displayRequest_logs_with_parameters_and_body() {
        LoggingServiceImp service = new LoggingServiceImp();
        ReflectionTestUtils.setField(service, "isTraceEnabled", true);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestURI()).thenReturn("/test");
        // parameter names enumeration
        when(request.getParameterNames()).thenReturn(Collections.enumeration(Collections.singletonList("p1")));
        when(request.getParameter("p1")).thenReturn("v1");

        service.displayRequest(request, "bodycontent");
        // nothing to assert on logger; we only exercise code paths
    }

    @Test
    @DisplayName("displayRequest does nothing when trace disabled")
    void displayRequest_nops_when_trace_disabled() {
        LoggingServiceImp service = new LoggingServiceImp();
        ReflectionTestUtils.setField(service, "isTraceEnabled", false);

        HttpServletRequest request = mock(HttpServletRequest.class);
        service.displayRequest(request, null);
    }

    @Test
    @DisplayName("displayResponse logs response when trace enabled and headers present")
    void displayResponse_logs_when_headers_present() {
        LoggingServiceImp service = new LoggingServiceImp();
        ReflectionTestUtils.setField(service, "isTraceEnabled", true);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getMethod()).thenReturn("GET");
        when(response.getHeaderNames()).thenReturn(Collections.singletonList("h1"));
        when(response.getHeader("h1")).thenReturn("v1");

        service.displayResponse(request, response, "resp");
    }

    @Test
    @DisplayName("displayResponse does nothing when trace disabled")
    void displayResponse_nops_when_trace_disabled() {
        LoggingServiceImp service = new LoggingServiceImp();
        ReflectionTestUtils.setField(service, "isTraceEnabled", false);

        service.displayResponse(null, null, null);
    }
}

