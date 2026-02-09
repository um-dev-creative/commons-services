package com.prx.commons.services.util;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

class PrinterUtilTest {

    @Test
    @DisplayName("Prints message when debug is enabled")
    void printsMessageWhenDebugIsEnabled() {
        PrinterUtil printerUtil = new PrinterUtil();
        Logger logger = mock(Logger.class);
        ReflectionTestUtils.setField(printerUtil, "isDebug", true);

        logger.info("Debug message");
        printerUtil.print("Debug message", logger);

        verify(logger, times(1)).info("Debug message");
    }

    @Test
    @DisplayName("Does not print message when debug is disabled")
    void doesNotPrintMessageWhenDebugIsDisabled() {
        PrinterUtil printerUtil = new PrinterUtil();
        Logger logger = mock(Logger.class);
        ReflectionTestUtils.setField(printerUtil, "isDebug", false);

        printerUtil.print("Debug message", logger);

        verify(logger, never()).info("Debug message");
    }

    @Test
    @DisplayName("Prints message when enable is true")
    void printsMessageWhenEnableIsTrue() {
        PrinterUtil printerUtil = new PrinterUtil();
        Logger logger = mock(Logger.class);
        logger.info("Enabled message");
        printerUtil.print("Enabled message", logger, true);

        verify(logger, times(1)).info("Enabled message");
    }

    @Test
    @DisplayName("Does not print message when enable is false")
    void doesNotPrintMessageWhenEnableIsFalse() {
        PrinterUtil printerUtil = new PrinterUtil();
        Logger logger = mock(Logger.class);

        printerUtil.print("Disabled message", logger, false);

        verify(logger, never()).info("Disabled message");
    }
}
