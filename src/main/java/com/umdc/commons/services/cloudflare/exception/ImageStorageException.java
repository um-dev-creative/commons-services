/*
 *  @(#)ImageStorageException.java
 *
 *  Copyright (c) Luis Antonio Mata Mata. All rights reserved.
 *
 *   All rights to this product are owned by Luis Antonio Mata Mata and may only
 *  be used under the terms of its associated license document. You may NOT
 *  copy, modify, sublicense, or distribute this source file or portions of
 *  it unless previously authorized in writing by Luis Antonio Mata Mata.
 *  In any event, this notice and the above copyright must always be included
 *  verbatim with this file.
 */

package com.umdc.commons.services.cloudflare.exception;

/**
 * Base checked exception for all image storage operations against
 * Cloudflare R2 (or any S3-compatible backend).
 *
 * <p>All specialised exceptions in this package extend this class so that
 * callers can catch either a specific subtype or this base type for
 * broad-stroke error handling:
 *
 * <pre>
 * try {
 *     service.upload(request);
 * } catch (ImageNotFoundException e) {
 *     // key does not exist
 * } catch (ImageStorageException e) {
 *     // any other storage failure
 * }
 * </pre>
 *
 * <p>All constructors propagate the original {@link Throwable} cause so that
 * the full stack trace is preserved (PMD {@code PreserveStackTrace} rule).
 */
public class ImageStorageException extends Exception {

    /**
     * Constructs an {@code ImageStorageException} with the specified detail message.
     *
     * @param message human-readable description of the failure
     */
    public ImageStorageException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code ImageStorageException} with the specified detail message
     * and root cause.
     *
     * @param message human-readable description of the failure
     * @param cause   the underlying exception that triggered this failure
     */
    public ImageStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code ImageStorageException} wrapping a root cause.
     *
     * @param cause the underlying exception that triggered this failure
     */
    public ImageStorageException(Throwable cause) {
        super(cause);
    }
}
