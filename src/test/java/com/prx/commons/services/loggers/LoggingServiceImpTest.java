package com.prx.commons.services.loggers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(value = {SpringExtension.class})
class LoggingServiceImpTest {

    @Mock
    private LoggingServiceImp loggingService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(loggingService, "isTraceEnabled", true);
    }

    @Test
    @DisplayName("Display request with valid inputs and trace enabled")
    void displayRequestWithValidInputsAndTraceEnabled() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Object body = new Object();

        request.getParameterMap().put("test", new String[]{"test"});
        request.getParameterMap().put("test2", new String[]{"test2"});

        Mockito.when(request.getMethod()).thenReturn("GET");
        Mockito.when(request.getRequestURI()).thenReturn("/test");

        loggingService.displayRequest(request, body);

        Mockito.verify(loggingService).displayRequest(request, body);
    }

    @Test
    @DisplayName("Display request with null body and trace enabled")
    void displayRequestWithNullBodyAndTraceEnabled() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        Mockito.when(request.getMethod()).thenReturn("GET");
        Mockito.when(request.getRequestURI()).thenReturn("/test");

        loggingService.displayRequest(request, null);

        Mockito.verify(loggingService).displayRequest(request, null);
    }

    @Test
    @DisplayName("Display response with valid inputs and trace enabled")
    void displayResponseWithValidInputsAndTraceEnabled() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Object body = new Object();

        Mockito.when(request.getMethod()).thenReturn("GET");

        loggingService.displayResponse(request, response, body);

        Mockito.verify(loggingService).displayResponse(request, response, body);
    }

    @Test
    @DisplayName("Display response with null body and trace enabled")
    void displayResponseWithNullBodyAndTraceEnabled() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        Mockito.when(request.getMethod()).thenReturn("GET");

        loggingService.displayResponse(request, response, null);

        Mockito.verify(loggingService).displayResponse(request, response, null);
    }

    @Test
    @DisplayName("Display response with null request and response and trace enabled")
    void displayResponseWithNullRequestAndResponseAndTraceEnabled() {
        loggingService.displayResponse(null, null, null);

        Mockito.verify(loggingService).displayResponse(null, null, null);
    }
}
