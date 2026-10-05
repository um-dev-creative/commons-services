/*
 *  @(#)ImageValidationException.java
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
 * Thrown when an {@link com.umdc.commons.services.cloudflare.to.ImageUploadRequest}
 * or an object key fails validation before any storage operation is attempted.
 * This exception maps to an HTTP {@code 400 Bad Request} response at the API layer.
 *
 * <p>Typical triggers:
 * <ul>
 *   <li>Empty or {@code null} image data</li>
 *   <li>Unsupported or missing content type</li>
 *   <li>Malformed object key (e.g. contains illegal characters)</li>
 *   <li>Image exceeding the configured maximum size</li>
 * </ul>
 *
 * <pre>
 * if (request.data() == null || request.data().length == 0) {
 *     throw new ImageValidationException("Image data must not be empty");
 * }
 * if (!ALLOWED_TYPES.contains(request.contentType())) {
 *     throw new ImageValidationException(
 *             "Unsupported content type: " + request.contentType());
 * }
 * </pre>
 */
public class ImageValidationException extends ImageStorageException {

    /**
     * Constructs an {@code ImageValidationException} with the specified detail message.
     *
     * @param message human-readable description of the validation failure
     */
    public ImageValidationException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code ImageValidationException} with the specified detail message
     * and root cause.
     *
     * @param message human-readable description of the validation failure
     * @param cause   the underlying exception that revealed the validation problem
     */
    public ImageValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code ImageValidationException} wrapping a root cause.
     *
     * @param cause the underlying exception that revealed the validation problem
     */
    public ImageValidationException(Throwable cause) {
        super(cause);
    }
}
