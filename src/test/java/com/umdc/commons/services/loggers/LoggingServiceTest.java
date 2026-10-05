package com.umdc.commons.services.loggers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LoggingServiceTest {

    @Test
    @DisplayName("Display request with valid inputs")
    void displayRequestWithValidInputs() {
        LoggingService loggingService = mock(LoggingService.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        Object body = new Object();

        loggingService.displayRequest(request, body);

        verify(loggingService).displayRequest(request, body);
    }

    @Test
    @DisplayName("Display request with null body")
    void displayRequestWithNullBody() {
        LoggingService loggingService = mock(LoggingService.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        loggingService.displayRequest(request, null);

        verify(loggingService).displayRequest(request, null);
    }

    @Test
    @DisplayName("Display response with valid inputs")
    void displayResponseWithValidInputs() {
        LoggingService loggingService = mock(LoggingService.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object body = new Object();

        loggingService.displayResponse(request, response, body);

        verify(loggingService).displayResponse(request, response, body);
    }

    @Test
    @DisplayName("Display response with null body")
    void displayResponseWithNullBody() {
        LoggingService loggingService = mock(LoggingService.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        loggingService.displayResponse(request, response, null);

        verify(loggingService).displayResponse(request, response, null);
    }

    @Test
    @DisplayName("Display response with null request and response")
    void displayResponseWithNullRequestAndResponse() {
        LoggingService loggingService = mock(LoggingService.class);

        loggingService.displayResponse(null, null, null);

        verify(loggingService).displayResponse(null, null, null);
    }
}
