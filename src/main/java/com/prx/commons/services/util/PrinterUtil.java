/*
 *  @(#)PrinterUtil.java
 *
 *  Copyright (c) Luis Antonio Mata Mata. All rights reserved.
 *
 *  All rights to this product are owned by Luis Antonio Mata Mata and may only
 *  be used under the terms of its associated license document. You may NOT
 *  copy, modify, sublicense, or distribute this source file or portions of
 *  it unless previously authorized in writing by Luis Antonio Mata Mata.
 *  In any event, this notice and the above copyright must always be included
 *  verbatim with this file.
 */

package com.prx.commons.services.util;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Utility that prints log messages when logging is enabled.
 * Provides two convenience methods to conditionally log messages.
 *
 * @author <a href='mailto:luis.antonio.mata@gmail.com'>Luis Antonio Mata</a>
 * @version 1.0.0, 29-09-2019
 */
@Service
public final class PrinterUtil {

    @Value("${log.debug}")
    private boolean isDebug;

    /**
     * Default constructor.
     */
    public PrinterUtil() {
        // Default constructor.
    }

    /**
     * Prints the object using the provided logger if the global debug flag is enabled.
     *
     * @param object the object to log
     * @param logger the logger to use
     */
    public void print(Object object, Logger logger){
        if(isDebug){
            logger.info(object);
        }
    }

    /**
     * Prints the object using the provided logger if the enable parameter is true.
     *
     * @param object the object to log
     * @param logger the logger to use
     * @param enable whether logging is enabled for this invocation
     */
    public void print(Object object, Logger logger, boolean enable){
        if(enable){
            logger.info(object);
        }
    }
}
