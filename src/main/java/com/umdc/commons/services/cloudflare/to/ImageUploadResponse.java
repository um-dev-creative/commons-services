/*
 *  @(#)ImageUploadResponse.java
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

package com.umdc.commons.services.cloudflare.to;

/**
 * Response returned after a successful image upload to Cloudflare R2
 * (or any compatible object storage backend).
 *
 * <p>Example:
 * <pre>
 *   new ImageUploadResponse(
 *       "profiles/user123/avatar.jpg",
 *       "https://pub-xxx.r2.dev/profiles/user123/avatar.jpg",
 *       "image/jpeg",
 *       45231L
 *   );
 * </pre>
 *
 * @param objectKey   the confirmed storage key / path under which the image
 *                    was stored in the bucket (may differ from the requested
 *                    key if the implementation auto-generates one).
 * @param publicUrl   publicly accessible URL for the uploaded image.
 *                    Will equal {@code objectKey} when no public-URL prefix
 *                    is configured ({@code cloudflare.r2.publicUrl}).
 * @param contentType MIME type of the stored image (e.g. {@code "image/jpeg"}).
 * @param size        number of bytes written to storage.
 */
public record ImageUploadResponse(
        String objectKey,
        String publicUrl,
        String contentType,
        long size) {
}
