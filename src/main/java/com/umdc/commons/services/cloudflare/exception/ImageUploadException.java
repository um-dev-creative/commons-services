/*
 *  @(#)ImageUploadException.java
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
 * Thrown when an image upload to Cloudflare R2 (or any S3-compatible storage)
 * fails. Typical root causes include:
 * <ul>
 *   <li>AWS SDK {@code S3Exception} — e.g. invalid credentials, bucket not found</li>
 *   <li>Network timeout or connectivity failure to the R2 endpoint</li>
 *   <li>Quota or rate-limit errors returned by the storage service</li>
 * </ul>
 *
 * <p>Always chain the original exception as the {@code cause} so the full
 * stack trace is available to diagnostics tooling.
 *
 * <pre>
 * try {
 *     r2Client.uploadImage(data, key, contentType);
 * } catch (S3Exception | IOException ex) {
 *     throw new ImageUploadException("Failed to upload image: " + key, ex);
 * }
 * </pre>
 */
public class ImageUploadException extends ImageStorageException {

    /**
     * Constructs an {@code ImageUploadException} with the specified detail message.
     *
     * @param message human-readable description of the upload failure
     */
    public ImageUploadException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code ImageUploadException} with the specified detail message
     * and root cause.
     *
     * @param message human-readable description of the upload failure
     * @param cause   the underlying exception (e.g. {@code S3Exception})
     */
    public ImageUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code ImageUploadException} wrapping a root cause.
     *
     * @param cause the underlying exception (e.g. {@code S3Exception})
     */
    public ImageUploadException(Throwable cause) {
        super(cause);
    }
}
