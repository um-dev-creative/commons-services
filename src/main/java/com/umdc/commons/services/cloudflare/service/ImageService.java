/*
 *  @(#)ProfileImageService.java
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

package com.umdc.commons.services.cloudflare.service;

import com.umdc.commons.services.cloudflare.exception.ImageDeleteException;
import com.umdc.commons.services.cloudflare.exception.ImageDownloadException;
import com.umdc.commons.services.cloudflare.exception.ImageNotFoundException;
import com.umdc.commons.services.cloudflare.exception.ImageStorageException;
import com.umdc.commons.services.cloudflare.exception.ImageUploadException;
import com.umdc.commons.services.cloudflare.exception.ImageValidationException;
import com.umdc.commons.services.cloudflare.to.ImageReferenceResponse;
import com.umdc.commons.services.cloudflare.to.ImageUploadRequest;
import com.umdc.commons.services.cloudflare.to.ImageUploadResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * General-purpose contract for managing image storage in Cloudflare R2
 * (or any S3-compatible object storage backend).
 *
 * <h2>Usage pattern</h2>
 * <p>This interface uses the <em>default-method stub</em> pattern: every
 * operation throws {@link UnsupportedOperationException} by default.
 * Consuming services implement only the operations they need:
 *
 * <pre>
 * {@literal @}Service
 * public class AvatarStorageService implements ProfileImageService {
 *
 *     private final CloudflareR2StorageClient r2;
 *
 *     public AvatarStorageService(CloudflareR2StorageClient r2) {
 *         this.r2 = r2;
 *     }
 *
 *     {@literal @}Override
 *     public ResponseEntity{@literal <}ImageUploadResponse{@literal >} upload(ImageUploadRequest request) {
 *         String key = request.objectKey() != null
 *                 ? request.objectKey()
 *                 : "avatars/" + UUID.randomUUID() + ".jpg";
 *         r2.uploadImage(request.data(), key, request.contentType());
 *         String url = r2.getPublicUrl(key);
 *         return ResponseEntity.status(HttpStatus.CREATED)
 *                 .body(new ImageUploadResponse(key, url, request.contentType(), request.data().length));
 *     }
 * }
 * </pre>
 *
 * <h2>Object-key conventions</h2>
 * <p>When {@link ImageUploadRequest#objectKey()} is {@code null} the implementation
 * is responsible for generating a unique, collision-resistant key. A recommended
 * convention is:
 * <pre>
 *   {context}/{entityId}/{uuid}.{ext}
 *   // e.g. "profiles/user-123/7f3b-....jpg"
 * </pre>
 */
public interface ImageService {

    // =========================================================================
    // Core operations
    // =========================================================================

    /**
     * Uploads an image to the storage system based on the provided request.
     *
     * @param request the {@link ImageUploadRequest} containing the image data and any metadata
     *                required for the upload process
     * @return {@code ResponseEntity<ImageUploadResponse>} containing details about the
     *         successfully uploaded image, such as its storage key, public URL, content type,
     *         and size
     * @throws ImageValidationException if the provided image data is invalid or fails validation
     * @throws ImageUploadException if an error occurs during the upload process
     */
    default ResponseEntity<ImageUploadResponse> upload(ImageUploadRequest request)
            throws ImageValidationException, ImageUploadException {
        throw new UnsupportedOperationException("upload not implemented");
    }

    /**
     * Downloads the image bytes from the storage corresponding to the given object key.
     *
     * @param objectKey the storage key/path of the image to download
     * @return {@code 200 OK} with the image bytes as a {@code byte[]} wrapped in a
     *         {@link ResponseEntity}, or an appropriate error status if the download fails
     * @throws ImageNotFoundException if the specified object key does not exist in the storage
     * @throws ImageDownloadException if there is an error during the download process
     */
    default ResponseEntity<byte[]> download(String objectKey)
            throws ImageNotFoundException, ImageDownloadException {
        throw new UnsupportedOperationException("download not implemented");
    }

    /**
     * Deletes the image identified by the specified storage key.
     *
     * @param objectKey the storage key of the image to delete
     * @return {@code 204 No Content} if the image was successfully deleted
     * @throws ImageNotFoundException if the specified object key does not exist in the storage
     * @throws ImageDeleteException if an error occurs during the deletion process
     */
    default ResponseEntity<Void> delete(String objectKey)
            throws ImageNotFoundException, ImageDeleteException {
        throw new UnsupportedOperationException("delete not implemented");
    }

    /**
     * Checks if an object with the specified key exists in the storage.
     *
     * @param objectKey the storage key of the object to check for existence
     * @return {@code ResponseEntity<Boolean>} indicating whether the object exists or not
     * @throws ImageStorageException if an error occurs while checking the existence of the object
     */
    default ResponseEntity<Boolean> exists(String objectKey) throws ImageStorageException {
        throw new UnsupportedOperationException("exists not implemented");
    }

    /**
     * Retrieves a reference to an image stored in the configured storage.
     *
     * @param objectKey the storage key/path of the image to retrieve the reference for
     * @return {@code ResponseEntity<ImageReferenceResponse>} containing the location
     *         and metadata of the stored image
     * @throws ImageNotFoundException if the specified object key does not exist in the storage
     * @throws ImageStorageException if an error occurs while retrieving the image reference
     */
    default ResponseEntity<ImageReferenceResponse> getReference(String objectKey)
            throws ImageStorageException {
        throw new UnsupportedOperationException("getReference not implemented");
    }

    /**
     * Retrieves a list of image references stored in the system that match the provided prefix.
     *
     * @param prefix the common prefix to filter images by, allowing partial matches
     *               against storage keys (e.g., folder paths or filename patterns)
     * @return {@code ResponseEntity<List<ImageReferenceResponse>>} containing a list of image references,
     *         each with its storage key, public URL, and content type metadata if available
     * @throws ImageStorageException if an error occurs while retrieving the image references from storage
     */
    default ResponseEntity<List<ImageReferenceResponse>> list(String prefix) throws ImageStorageException {
        throw new UnsupportedOperationException("list not implemented");
    }
}
