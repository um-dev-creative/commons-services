package com.prx.commons.services.loggers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class LoggingServiceTest {

    @Test
    @DisplayName("Display request with valid inputs")
    void displayRequestWithValidInputs() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Object body = new Object();

        loggingService.displayRequest(request, body);

        Mockito.verify(loggingService).displayRequest(request, body);
    }

    @Test
    @DisplayName("Display request with null body")
    void displayRequestWithNullBody() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        loggingService.displayRequest(request, null);

        Mockito.verify(loggingService).displayRequest(request, null);
    }

    @Test
    @DisplayName("Display response with valid inputs")
    void displayResponseWithValidInputs() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Object body = new Object();

        loggingService.displayResponse(request, response, body);

        Mockito.verify(loggingService).displayResponse(request, response, body);
    }

    @Test
    @DisplayName("Display response with null body")
    void displayResponseWithNullBody() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        loggingService.displayResponse(request, response, null);

        Mockito.verify(loggingService).displayResponse(request, response, null);
    }

    @Test
    @DisplayName("Display response with null request and response")
    void displayResponseWithNullRequestAndResponse() {
        LoggingService loggingService = Mockito.mock(LoggingService.class);

        loggingService.displayResponse(null, null, null);

        Mockito.verify(loggingService).displayResponse(null, null, null);
    }
}
