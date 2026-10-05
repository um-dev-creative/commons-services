/*
 *  @(#)ImageNotFoundException.java
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
 * Thrown when a requested image object key does not exist in Cloudflare R2
 * (or any S3-compatible storage). This exception maps to an HTTP {@code 404 Not Found}
 * response at the API layer.
 *
 * <p>Use this exception in preference to {@link ImageDownloadException} or
 * {@link ImageDeleteException} when the root cause is specifically a missing key —
 * it allows callers to distinguish "key not found" from "storage error":
 *
 * <pre>
 * try {
 *     return r2Client.downloadImage(objectKey);
 * } catch (S3Exception ex) {
 *     if (ex.statusCode() == 404) {
 *         throw new ImageNotFoundException(objectKey);
 *     }
 *     throw new ImageDownloadException("Download failed for key: " + objectKey, ex);
 * }
 * </pre>
 *
 * <p>The {@code objectKey} parameter is stored and exposed via {@link #getObjectKey()}
 * to support structured logging and error responses without string parsing.
 */
public class ImageNotFoundException extends ImageStorageException {

    private final String objectKey;

    /**
     * Constructs an {@code ImageNotFoundException} for the given object key.
     *
     * @param objectKey the storage key that was not found
     */
    public ImageNotFoundException(String objectKey) {
        super("Image not found for key: " + objectKey);
        this.objectKey = objectKey;
    }

    /**
     * Constructs an {@code ImageNotFoundException} for the given object key,
     * chaining the underlying storage exception as the cause.
     *
     * @param objectKey the storage key that was not found
     * @param cause     the underlying exception (e.g. {@code S3Exception} with HTTP 404)
     */
    public ImageNotFoundException(String objectKey, Throwable cause) {
        super("Image not found for key: " + objectKey, cause);
        this.objectKey = objectKey;
    }

    /**
     * Returns the storage key that was not found.
     *
     * @return the object key passed at construction time
     */
    public String getObjectKey() {
        return objectKey;
    }
}
