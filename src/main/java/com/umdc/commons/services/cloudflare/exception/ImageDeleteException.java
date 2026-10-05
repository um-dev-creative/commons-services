/*
 *  @(#)ImageDeleteException.java
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
 * Thrown when an image deletion from Cloudflare R2 (or any S3-compatible storage)
 * fails. Typical root causes include:
 * <ul>
 *   <li>AWS SDK {@code S3Exception} — e.g. access denied, service unavailable</li>
 *   <li>Network timeout or connectivity failure to the R2 endpoint</li>
 * </ul>
 *
 * <p>Note: when the requested object key does not exist, throw
 * {@link ImageNotFoundException} instead of this exception.
 *
 * <pre>
 * try {
 *     r2Client.deleteImage(objectKey);
 * } catch (S3Exception ex) {
 *     if (ex.statusCode() == 404) throw new ImageNotFoundException(objectKey);
 *     throw new ImageDeleteException("Failed to delete image: " + objectKey, ex);
 * }
 * </pre>
 */
public class ImageDeleteException extends ImageStorageException {

    /**
     * Constructs an {@code ImageDeleteException} with the specified detail message.
     *
     * @param message human-readable description of the deletion failure
     */
    public ImageDeleteException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code ImageDeleteException} with the specified detail message
     * and root cause.
     *
     * @param message human-readable description of the deletion failure
     * @param cause   the underlying exception (e.g. {@code S3Exception})
     */
    public ImageDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code ImageDeleteException} wrapping a root cause.
     *
     * @param cause the underlying exception (e.g. {@code S3Exception})
     */
    public ImageDeleteException(Throwable cause) {
        super(cause);
    }
}
