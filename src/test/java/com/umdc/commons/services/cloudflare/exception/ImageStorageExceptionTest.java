package com.umdc.commons.services.cloudflare.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@DisplayName("ImageStorageException hierarchy")
class ImageStorageExceptionTest {

    // =========================================================================
    // ImageStorageException (base)
    // =========================================================================

    @Test
    @DisplayName("ImageStorageException(message) preserves message")
    void baseMessageConstructor() {
        ImageStorageException ex = new ImageStorageException("storage failure");
        assertEquals("storage failure", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    @DisplayName("ImageStorageException(message, cause) chains cause")
    void baseMessageCauseConstructor() {
        RuntimeException root = new RuntimeException("root");
        ImageStorageException ex = new ImageStorageException("wrapped", root);
        assertEquals("wrapped", ex.getMessage());
        assertSame(root, ex.getCause());
    }

    @Test
    @DisplayName("ImageStorageException(cause) wraps cause")
    void baseCauseConstructor() {
        RuntimeException root = new RuntimeException("root");
        ImageStorageException ex = new ImageStorageException(root);
        assertSame(root, ex.getCause());
    }

    // =========================================================================
    // ImageUploadException
    // =========================================================================

    @Test
    @DisplayName("ImageUploadException extends ImageStorageException")
    void uploadExceptionIsStorageException() {
        assertInstanceOf(ImageStorageException.class, new ImageUploadException("fail"));
    }

    @Test
    @DisplayName("ImageUploadException(message) preserves message")
    void uploadMessageConstructor() {
        ImageUploadException ex = new ImageUploadException("upload failed");
        assertEquals("upload failed", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    @DisplayName("ImageUploadException(message, cause) chains cause")
    void uploadMessageCauseConstructor() {
        RuntimeException root = new RuntimeException("s3 error");
        ImageUploadException ex = new ImageUploadException("upload failed", root);
        assertEquals("upload failed", ex.getMessage());
        assertSame(root, ex.getCause());
    }

    @Test
    @DisplayName("ImageUploadException(cause) wraps cause")
    void uploadCauseConstructor() {
        RuntimeException root = new RuntimeException("s3 error");
        ImageUploadException ex = new ImageUploadException(root);
        assertSame(root, ex.getCause());
    }

    // =========================================================================
    // ImageDownloadException
    // =========================================================================

    @Test
    @DisplayName("ImageDownloadException extends ImageStorageException")
    void downloadExceptionIsStorageException() {
        assertInstanceOf(ImageStorageException.class, new ImageDownloadException("fail"));
    }

    @Test
    @DisplayName("ImageDownloadException(message) preserves message")
    void downloadMessageConstructor() {
        ImageDownloadException ex = new ImageDownloadException("download failed");
        assertEquals("download failed", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    @DisplayName("ImageDownloadException(message, cause) chains cause")
    void downloadMessageCauseConstructor() {
        RuntimeException root = new RuntimeException("io error");
        ImageDownloadException ex = new ImageDownloadException("download failed", root);
        assertEquals("download failed", ex.getMessage());
        assertSame(root, ex.getCause());
    }

    @Test
    @DisplayName("ImageDownloadException(cause) wraps cause")
    void downloadCauseConstructor() {
        RuntimeException root = new RuntimeException("io error");
        ImageDownloadException ex = new ImageDownloadException(root);
        assertSame(root, ex.getCause());
    }

    // =========================================================================
    // ImageDeleteException
    // =========================================================================

    @Test
    @DisplayName("ImageDeleteException extends ImageStorageException")
    void deleteExceptionIsStorageException() {
        assertInstanceOf(ImageStorageException.class, new ImageDeleteException("fail"));
    }

    @Test
    @DisplayName("ImageDeleteException(message) preserves message")
    void deleteMessageConstructor() {
        ImageDeleteException ex = new ImageDeleteException("delete failed");
        assertEquals("delete failed", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    @DisplayName("ImageDeleteException(message, cause) chains cause")
    void deleteMessageCauseConstructor() {
        RuntimeException root = new RuntimeException("s3 error");
        ImageDeleteException ex = new ImageDeleteException("delete failed", root);
        assertEquals("delete failed", ex.getMessage());
        assertSame(root, ex.getCause());
    }

    @Test
    @DisplayName("ImageDeleteException(cause) wraps cause")
    void deleteCauseConstructor() {
        RuntimeException root = new RuntimeException("s3 error");
        ImageDeleteException ex = new ImageDeleteException(root);
        assertSame(root, ex.getCause());
    }

    // =========================================================================
    // ImageNotFoundException
    // =========================================================================

    @Test
    @DisplayName("ImageNotFoundException extends ImageStorageException")
    void notFoundExceptionIsStorageException() {
        assertInstanceOf(ImageStorageException.class, new ImageNotFoundException("key"));
    }

    @Test
    @DisplayName("ImageNotFoundException(objectKey) embeds key in message and exposes it")
    void notFoundKeyConstructor() {
        ImageNotFoundException ex = new ImageNotFoundException("profiles/user1/avatar.jpg");
        assertEquals("profiles/user1/avatar.jpg", ex.getObjectKey());
        assertEquals("Image not found for key: profiles/user1/avatar.jpg", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    @DisplayName("ImageNotFoundException(objectKey, cause) chains cause and exposes key")
    void notFoundKeyCauseConstructor() {
        RuntimeException root = new RuntimeException("s3 404");
        ImageNotFoundException ex = new ImageNotFoundException("profiles/user1/avatar.jpg", root);
        assertEquals("profiles/user1/avatar.jpg", ex.getObjectKey());
        assertSame(root, ex.getCause());
    }

    // =========================================================================
    // ImageValidationException
    // =========================================================================

    @Test
    @DisplayName("ImageValidationException extends ImageStorageException")
    void validationExceptionIsStorageException() {
        assertInstanceOf(ImageStorageException.class, new ImageValidationException("invalid"));
    }

    @Test
    @DisplayName("ImageValidationException(message) preserves message")
    void validationMessageConstructor() {
        ImageValidationException ex = new ImageValidationException("unsupported content type");
        assertEquals("unsupported content type", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    @DisplayName("ImageValidationException(message, cause) chains cause")
    void validationMessageCauseConstructor() {
        RuntimeException root = new RuntimeException("parse error");
        ImageValidationException ex = new ImageValidationException("invalid image data", root);
        assertEquals("invalid image data", ex.getMessage());
        assertSame(root, ex.getCause());
    }

    @Test
    @DisplayName("ImageValidationException(cause) wraps cause")
    void validationCauseConstructor() {
        RuntimeException root = new RuntimeException("parse error");
        ImageValidationException ex = new ImageValidationException(root);
        assertSame(root, ex.getCause());
    }
}
