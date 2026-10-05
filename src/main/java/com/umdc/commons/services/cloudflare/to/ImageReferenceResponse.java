/*
 *  @(#)ImageReferenceResponse.java
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
 * Response containing the location and metadata of a stored image in
 * Cloudflare R2 (or any compatible object storage backend).
 *
 * <p>Returned by reference-lookup and list operations, allowing callers
 * to resolve the public URL without downloading the image bytes.
 *
 * <p>Example:
 * <pre>
 *   new ImageReferenceResponse(
 *       "profiles/user123/avatar.jpg",
 *       "https://pub-xxx.r2.dev/profiles/user123/avatar.jpg",
 *       "image/jpeg"
 *   );
 * </pre>
 *
 * @param objectKey   the storage key / path of the image in the bucket.
 * @param publicUrl   publicly accessible URL for the image.
 *                    Will equal {@code objectKey} when no public-URL prefix
 *                    is configured ({@code cloudflare.r2.publicUrl}).
 * @param contentType MIME type of the image (e.g. {@code "image/jpeg"}).
 *                    May be {@code null} when unavailable (e.g. from a
 *                    list operation that does not fetch per-object metadata).
 */
public record ImageReferenceResponse(
        String objectKey,
        String publicUrl,
        String contentType) {
}
