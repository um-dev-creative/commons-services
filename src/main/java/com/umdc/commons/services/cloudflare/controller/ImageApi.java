/*
 *  @(#)ProfileImageApi.java
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

package com.umdc.commons.services.cloudflare.controller;

import com.umdc.commons.services.cloudflare.exception.ImageDeleteException;
import com.umdc.commons.services.cloudflare.exception.ImageDownloadException;
import com.umdc.commons.services.cloudflare.exception.ImageNotFoundException;
import com.umdc.commons.services.cloudflare.exception.ImageStorageException;
import com.umdc.commons.services.cloudflare.exception.ImageUploadException;
import com.umdc.commons.services.cloudflare.exception.ImageValidationException;
import com.umdc.commons.services.cloudflare.service.ImageService;
import com.umdc.commons.services.cloudflare.to.ImageReferenceResponse;
import com.umdc.commons.services.cloudflare.to.ImageUploadRequest;
import com.umdc.commons.services.cloudflare.to.ImageUploadResponse;
import com.umdc.commons.util.HttpStatusUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;

/**
 * REST API contract for managing image storage in a Cloudflare R2 bucket
 * (or any S3-compatible object storage backend).
 *
 * <p>All operations require a {@value #SESSION_TOKEN_KEY} request header for
 * authentication. Consuming services implement this interface on a
 * {@code @RestController} and declare their own {@code @RequestMapping} base path.
 *
 * <h2>Implementation example</h2>
 * <pre>
 * {@literal @}RestController
 * {@literal @}RequestMapping("/v1/images")
 * public class AvatarController implements ProfileImageApi {
 *
 *     private final AvatarStorageService service;
 *
 *     public AvatarController(AvatarStorageService service) {
 *         this.service = service;
 *     }
 *
 *     {@literal @}Override
 *     public ProfileImageService getService() { return service; }
 *
 *     {@literal @}Override
 *     public ResponseEntity{@literal <}ImageUploadResponse{@literal >} upload(
 *             String token, String objectKey, byte[] image, String contentType) throws Exception {
 *         return service.upload(ImageUploadRequest.of(objectKey, image, contentType));
 *     }
 * }
 * </pre>
 *
 * <h2>Endpoint summary</h2>
 * <table>
 *   <tr><th>Method</th><th>Path</th><th>Description</th></tr>
 *   <tr><td>POST</td><td>/upload</td><td>Upload an image</td></tr>
 *   <tr><td>GET</td><td>/download</td><td>Download image bytes</td></tr>
 *   <tr><td>DELETE</td><td>/delete</td><td>Delete an image</td></tr>
 *   <tr><td>GET</td><td>/exists</td><td>Check image existence</td></tr>
 *   <tr><td>GET</td><td>/reference</td><td>Get image reference / public URL</td></tr>
 *   <tr><td>GET</td><td>/list</td><td>List images by prefix</td></tr>
 * </table>
 */
public interface ImageApi {

    /** Request header key used to pass the caller's session token. */
    String SESSION_TOKEN_KEY = "session-token";

    /** OpenAPI parameter description for the session token header. */
    String SESSION_TOKEN_DESCRIPTION = "Session token";

    /** OpenAPI response description for missing or invalid session token. */
    String UNAUTHORIZED_DESCRIPTION = "Missing or invalid session token";

    /**
     * Returns the service implementation to which default methods delegate.
     * Override in the implementing controller to supply the concrete service.
     *
     * @return a no-op {@link ImageService} by default
     */
    default ImageService getService() {
        return new ImageService() {
        };
    }

    // =========================================================================
    // Core endpoints
    // =========================================================================

    /**
     * Uploads an image to Cloudflare R2 storage. This method allows the client to upload
     * image data, optionally specifying a storage key (objectKey) to define the path where
     * the image will be stored. When the objectKey is omitted, the service will generate one
     * automatically. The image must be provided as raw bytes, and the MIME type can be specified.
     *
     * @param token       The session token used for authentication. This parameter is required.
     * @param objectKey   The storage key or path in the bucket for the image. This parameter
     *                    is optional, and if omitted, a key will be automatically generated.
     * @param image       The raw bytes of the image to be uploaded. This parameter is required.
     * @param contentType The MIME type of the image, such as "image/jpeg". This parameter
     *                    is optional and defaults to "image/jpeg" if not provided.
     * @return A ResponseEntity containing an ImageUploadResponse if the upload is successful,
     *         or an appropriate HTTP response code and error message in case of failure.
     * @throws ImageValidationException If validation of the image fails, such as invalid format
     *                                  or unsupported file type.
     * @throws ImageUploadException     If an error occurs during the upload process, such as
     *                                  failure to store the image in Cloudflare R2.
     */
    @Operation(
        summary = "Upload image",
        description = "Uploads an image to Cloudflare R2. " +
                      "Provide an explicit objectKey to control the storage path, " +
                      "or omit it to have the service generate one.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = HttpStatusUtil.CREATED_STR,
                     description = "Image uploaded successfully"),
        @ApiResponse(responseCode = HttpStatusUtil.BAD_REQUEST_STR,
                     description = "Missing or invalid image data"),
        @ApiResponse(responseCode = HttpStatusUtil.UNAUTHORIZED_STR,
                     description = UNAUTHORIZED_DESCRIPTION),
        @ApiResponse(responseCode = HttpStatusUtil.INTERNAL_SERVER_ERROR_STR,
                     description = "Upload failed due to a server error")
    })
    @PostMapping(value = "/upload",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<ImageUploadResponse> upload(
            @Parameter(description = SESSION_TOKEN_DESCRIPTION, required = true)
            @RequestHeader(SESSION_TOKEN_KEY) String token,
            @Parameter(description = "Storage key / path in the bucket. Auto-generated when omitted.")
            @RequestPart(required = false) String objectKey,
            @Parameter(description = "Raw image bytes", required = true)
            @RequestPart byte[] image,
            @Parameter(description = "MIME type of the image (defaults to image/jpeg)")
            @RequestPart(required = false) String contentType)
            throws ImageValidationException, ImageUploadException {
        try {
            return getService().upload(ImageUploadRequest.of(
                    objectKey, image, contentType != null ? contentType : MediaType.IMAGE_JPEG_VALUE));
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
    }

    /**
     * Downloads the image corresponding to the given objectKey and returns the raw bytes of the image.
     *
     * @param token the session token required for authentication
     * @param objectKey the storage key or path of the image to be downloaded
     * @return a ResponseEntity containing the raw bytes of the image, or an appropriate HTTP status if the operation fails
     * @throws ImageNotFoundException if no image is found for the specified objectKey
     * @throws ImageDownloadException if the download fails due to a server-side error
     */
    @Operation(
        summary = "Download image",
        description = "Returns the raw bytes of the image stored at the given objectKey.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = HttpStatusUtil.OK_STR,
                     description = "Image bytes returned"),
        @ApiResponse(responseCode = HttpStatusUtil.NOT_FOUND_STR,
                     description = "No image found for the given objectKey"),
        @ApiResponse(responseCode = HttpStatusUtil.UNAUTHORIZED_STR,
                     description = UNAUTHORIZED_DESCRIPTION),
        @ApiResponse(responseCode = HttpStatusUtil.INTERNAL_SERVER_ERROR_STR,
                     description = "Download failed due to a server error")
    })
    @GetMapping(value = "/download")
    default ResponseEntity<byte[]> download(
            @Parameter(description = SESSION_TOKEN_DESCRIPTION, required = true)
            @RequestHeader(SESSION_TOKEN_KEY) String token,
            @Parameter(description = "Storage key / path of the image", required = true)
            @RequestParam String objectKey)
            throws ImageNotFoundException, ImageDownloadException {
        try {
            return getService().download(objectKey);
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
    }

    /**
     * Permanently deletes the image identified by the given objectKey from the storage bucket.
     *
     * @param token the session token used for authentication and authorization
     * @param objectKey the unique storage key or path of the image to be deleted
     * @return a {@link ResponseEntity} with appropriate HTTP status:
     *         - 204 if the image is successfully deleted
     *         - 404 if no image is found for the given objectKey
     *         - 401 if the session token is missing or invalid
     *         - 500 if the deletion fails due to a server error
     * @throws ImageNotFoundException if the image to delete does not exist
     * @throws ImageDeleteException if there is an error during the deletion process
     */
    @Operation(
        summary = "Delete image",
        description = "Permanently removes the image at the given objectKey from the bucket.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
                     description = "Image deleted"),
        @ApiResponse(responseCode = HttpStatusUtil.NOT_FOUND_STR,
                     description = "No image found for the given objectKey"),
        @ApiResponse(responseCode = HttpStatusUtil.UNAUTHORIZED_STR,
                     description = UNAUTHORIZED_DESCRIPTION),
        @ApiResponse(responseCode = HttpStatusUtil.INTERNAL_SERVER_ERROR_STR,
                     description = "Deletion failed due to a server error")
    })
    @DeleteMapping(value = "/delete")
    default ResponseEntity<Void> delete(
            @Parameter(description = SESSION_TOKEN_DESCRIPTION, required = true)
            @RequestHeader(SESSION_TOKEN_KEY) String token,
            @Parameter(description = "Storage key / path of the image to delete", required = true)
            @RequestParam String objectKey)
            throws ImageNotFoundException, ImageDeleteException {
        try {
            return getService().delete(objectKey);
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
    }

    /**
     * Checks whether an image exists at the specified storage key.
     *
     * @param token the session token used for authentication
     * @param objectKey the storage key or path to check for the image existence
     * @return a ResponseEntity containing a Boolean value; true if the image exists,
     *         false otherwise
     * @throws ImageStorageException if an error occurs during the existence check
     */
    @Operation(
        summary = "Check image existence",
        description = "Returns true if an image is stored at the given objectKey, false otherwise.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = HttpStatusUtil.OK_STR,
                     description = "Existence check result returned"),
        @ApiResponse(responseCode = HttpStatusUtil.UNAUTHORIZED_STR,
                     description = UNAUTHORIZED_DESCRIPTION),
        @ApiResponse(responseCode = HttpStatusUtil.INTERNAL_SERVER_ERROR_STR,
                     description = "Check failed due to a server error")
    })
    @GetMapping(value = "/exists", produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<Boolean> exists(
            @Parameter(description = SESSION_TOKEN_DESCRIPTION, required = true)
            @RequestHeader(SESSION_TOKEN_KEY) String token,
            @Parameter(description = "Storage key / path to check", required = true)
            @RequestParam String objectKey) throws ImageStorageException {
        try {
            return getService().exists(objectKey);
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
    }

    /**
     * Retrieves the public URL and metadata for the image specified by the given objectKey.
     * This method does not download the image bytes but provides information necessary
     * to reference the image.
     *
     * @param token     the session token used for authentication, required for accessing the image
     * @param objectKey the storage key or path of the image to retrieve the reference for, required
     * @return a ResponseEntity containing ImageReferenceResponse with public URL and metadata if the image is found;
     *         or an appropriate HTTP status if the image does not exist, authentication fails, or an internal error occurs
     * @throws ImageNotFoundException      if no image is found for the specified objectKey
     * @throws ImageStorageException       if an error occurs during the image lookup process
     */
    @Operation(
        summary = "Get image reference",
        description = "Returns the public URL and metadata for the image at the given objectKey " +
                      "without downloading the image bytes.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = HttpStatusUtil.OK_STR,
                     description = "Image reference returned"),
        @ApiResponse(responseCode = HttpStatusUtil.NOT_FOUND_STR,
                     description = "No image found for the given objectKey"),
        @ApiResponse(responseCode = HttpStatusUtil.UNAUTHORIZED_STR,
                     description = UNAUTHORIZED_DESCRIPTION),
        @ApiResponse(responseCode = HttpStatusUtil.INTERNAL_SERVER_ERROR_STR,
                     description = "Lookup failed due to a server error")
    })
    @GetMapping(value = "/reference", produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<ImageReferenceResponse> getReference(
            @Parameter(description = SESSION_TOKEN_DESCRIPTION, required = true)
            @RequestHeader(SESSION_TOKEN_KEY) String token,
            @Parameter(description = "Storage key / path of the image", required = true)
            @RequestParam String objectKey)
            throws ImageStorageException {
        try {
            return getService().getReference(objectKey);
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
    }

    /**
     * Lists image references stored in the bucket. Returns all image references whose objectKey
     * starts with the specified prefix. If no prefix is provided, all images in the bucket are listed.
     *
     * @param token the session token used for authentication (required)
     * @param prefix the optional key prefix used to filter the image references
     * @return a {@code ResponseEntity} containing a list of {@code ImageReferenceResponse}.
     *         The list may be empty if no images match the specified prefix.
     * @throws ImageStorageException if an error occurs while listing the images
     */
    @Operation(
        summary = "List images",
        description = "Returns all image references whose objectKey starts with the given prefix. " +
                      "Omit prefix to list all images in the bucket.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = HttpStatusUtil.OK_STR,
                     description = "Image list returned (may be empty)"),
        @ApiResponse(responseCode = HttpStatusUtil.UNAUTHORIZED_STR,
                     description = UNAUTHORIZED_DESCRIPTION),
        @ApiResponse(responseCode = HttpStatusUtil.INTERNAL_SERVER_ERROR_STR,
                     description = "Listing failed due to a server error")
    })
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<List<ImageReferenceResponse>> list(
            @Parameter(description = SESSION_TOKEN_DESCRIPTION, required = true)
            @RequestHeader(SESSION_TOKEN_KEY) String token,
            @Parameter(description = "Key prefix to filter results (optional)")
            @RequestParam(required = false) String prefix) throws ImageStorageException {
        try {
            return getService().list(prefix);
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
    }
}
