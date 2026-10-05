package com.umdc.commons.services.loggers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
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
        HttpServletRequest request = mock(HttpServletRequest.class);
        Object body = new Object();

        loggingService.displayRequest(request, body);

        verify(loggingService).displayRequest(request, body);
    }

    @Test
    @DisplayName("Display request with null body and trace enabled")
    void displayRequestWithNullBodyAndTraceEnabled() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        loggingService.displayRequest(request, null);

        verify(loggingService).displayRequest(request, null);
    }

    @Test
    @DisplayName("Display response with valid inputs and trace enabled")
    void displayResponseWithValidInputsAndTraceEnabled() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object body = new Object();

        loggingService.displayResponse(request, response, body);

        verify(loggingService).displayResponse(request, response, body);
    }

    @Test
    @DisplayName("Display response with null body and trace enabled")
    void displayResponseWithNullBodyAndTraceEnabled() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        loggingService.displayResponse(request, response, null);

        verify(loggingService).displayResponse(request, response, null);
    }

    @Test
    @DisplayName("Display response with null request and response and trace enabled")
    void displayResponseWithNullRequestAndResponseAndTraceEnabled() {
        loggingService.displayResponse(null, null, null);

        verify(loggingService).displayResponse(null, null, null);
    }
}
