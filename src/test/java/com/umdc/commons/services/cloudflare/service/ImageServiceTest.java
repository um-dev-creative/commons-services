package com.umdc.commons.services.cloudflare.service;

import com.umdc.commons.services.cloudflare.to.ImageUploadRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ImageService default stubs")
class ImageServiceTest {

    /**
     * Anonymous implementation that overrides nothing — exercises all default stubs.
     */
    private final ImageService service = new ImageService() {
    };

    @Test
    @DisplayName("upload default stub throws UnsupportedOperationException")
    void uploadThrowsUnsupported() {
        ImageUploadRequest req = ImageUploadRequest.of(new byte[]{1}, "image/jpeg");
        assertThrows(UnsupportedOperationException.class, () -> service.upload(req));
    }

    @Test
    @DisplayName("download default stub throws UnsupportedOperationException")
    void downloadThrowsUnsupported() {
        assertThrows(UnsupportedOperationException.class, () -> service.download("some/key.jpg"));
    }

    @Test
    @DisplayName("delete default stub throws UnsupportedOperationException")
    void deleteThrowsUnsupported() {
        assertThrows(UnsupportedOperationException.class, () -> service.delete("some/key.jpg"));
    }

    @Test
    @DisplayName("exists default stub throws UnsupportedOperationException")
    void existsThrowsUnsupported() {
        assertThrows(UnsupportedOperationException.class, () -> service.exists("some/key.jpg"));
    }

    @Test
    @DisplayName("getReference default stub throws UnsupportedOperationException")
    void getReferenceThrowsUnsupported() {
        assertThrows(UnsupportedOperationException.class, () -> service.getReference("some/key.jpg"));
    }

    @Test
    @DisplayName("list default stub throws UnsupportedOperationException")
    void listThrowsUnsupported() {
        assertThrows(UnsupportedOperationException.class, () -> service.list("profiles/"));
    }
}
