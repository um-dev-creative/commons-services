/*
 *  @(#)ImageUploadRequest.java
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Request object carrying all information needed to upload an image to
 * Cloudflare R2 (or any compatible object storage backend).
 *
 * <p>Use the static factory methods to construct instances:
 * <pre>
 *   // auto-generated key
 *   ImageUploadRequest.of(bytes, "image/jpeg");
 *
 *   // explicit key
 *   ImageUploadRequest.of("profiles/user123/avatar.jpg", bytes, "image/jpeg");
 *
 *   // with custom metadata
 *   ImageUploadRequest.of("profiles/user123/avatar.jpg", bytes, "image/jpeg",
 *       Map.of("userId", "user123", "source", "mobile-app"));
 * </pre>
 *
 * <p><b>Note on equality:</b> because {@code data} is a {@code byte[]} array,
 * record equality uses reference comparison for that component. This is
 * intentional — this class is a carrier DTO, not a value type.
 *
 * @param objectKey   storage key / path in the bucket
 *                    (e.g. {@code "profiles/user123/avatar.jpg"}).
 *                    May be {@code null}; implementations should auto-generate
 *                    a unique key when this is absent.
 * @param data        raw image bytes. Must not be {@code null} or empty.
 * @param contentType MIME type of the image (e.g. {@code "image/jpeg"},
 *                    {@code "image/png"}, {@code "image/webp"}).
 *                    Must not be {@code null}.
 * @param metadata    optional map of custom object metadata to store alongside
 *                    the image (e.g. user ID, source system, upload context).
 *                    May be {@code null}; treated as empty if absent.
 */
public record ImageUploadRequest(
        String objectKey,
        byte[] data,
        String contentType,
        Map<String, String> metadata) {

    /**
     * Compact canonical constructor — validates required fields and
     * performs a defensive copy of the byte array to prevent external mutation.
     */
    public ImageUploadRequest {
        Objects.requireNonNull(data, "data must not be null");
        if (data.length == 0) {
            throw new IllegalArgumentException("data must not be empty");
        }
        Objects.requireNonNull(contentType, "contentType must not be null");
        data = data.clone();
        metadata = metadata != null ? Collections.unmodifiableMap(metadata) : Collections.emptyMap();
    }

    /**
     * Creates a request with auto-generated object key (no metadata).
     *
     * @param data        raw image bytes
     * @param contentType MIME type (e.g. {@code "image/jpeg"})
     * @return new request with {@code objectKey = null}
     */
    public static ImageUploadRequest of(byte[] data, String contentType) {
        return new ImageUploadRequest(null, data, contentType, null);
    }

    /**
     * Creates a request with an explicit object key (no metadata).
     *
     * @param objectKey   storage key / path in the bucket
     * @param data        raw image bytes
     * @param contentType MIME type (e.g. {@code "image/jpeg"})
     * @return new request
     */
    public static ImageUploadRequest of(String objectKey, byte[] data, String contentType) {
        return new ImageUploadRequest(objectKey, data, contentType, null);
    }

    /**
     * Creates a request with an explicit object key and custom metadata.
     *
     * @param objectKey   storage key / path in the bucket
     * @param data        raw image bytes
     * @param contentType MIME type (e.g. {@code "image/jpeg"})
     * @param metadata    custom metadata to store alongside the object
     * @return new request
     */
    public static ImageUploadRequest of(String objectKey, byte[] data, String contentType,
                                        Map<String, String> metadata) {
        return new ImageUploadRequest(objectKey, data, contentType, metadata);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageUploadRequest(String key, byte[] data1, String type, Map<String, String> metadata1))) return false;
        return Objects.equals(objectKey, key)
                && Arrays.equals(data, data1)
                && Objects.equals(contentType, type)
                && Objects.equals(metadata, metadata1);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(objectKey, contentType, metadata);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "ImageUploadRequest[objectKey=" + objectKey
                + ", data=" + Arrays.toString(data)
                + ", contentType=" + contentType
                + ", metadata=" + metadata + "]";
    }
}
