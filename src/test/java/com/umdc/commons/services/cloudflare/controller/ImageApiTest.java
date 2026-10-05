package com.umdc.commons.services.cloudflare.controller;

import com.umdc.commons.services.cloudflare.service.ImageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("ImageApi default stubs")
class ImageApiTest {

    /**
     * Anonymous implementation that overrides nothing — exercises all default stubs.
     */
    private final ImageApi api = new ImageApi() {
    };

    private static final String TOKEN = "test-token";
    private static final String KEY = "profiles/user1/avatar.jpg";
    private static final byte[] IMAGE = new byte[]{1, 2, 3};

    @Test
    @DisplayName("getService returns a non-null ImageService no-op instance")
    void getServiceReturnsNoOpInstance() {
        assertNotNull(api.getService());
        assertNotNull(api.getService());
    }

    @Test
    @DisplayName("upload default stub returns 501 Not Implemented")
    void uploadReturnsNotImplemented() throws Exception {
        var response = api.upload(TOKEN, KEY, IMAGE, "image/jpeg");
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    @Test
    @DisplayName("upload with null objectKey returns 501 Not Implemented")
    void uploadNullKeyReturnsNotImplemented() throws Exception {
        var response = api.upload(TOKEN, null, IMAGE, null);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    @Test
    @DisplayName("download default stub returns 501 Not Implemented")
    void downloadReturnsNotImplemented() throws Exception {
        var response = api.download(TOKEN, KEY);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    @Test
    @DisplayName("delete default stub returns 501 Not Implemented")
    void deleteReturnsNotImplemented() throws Exception {
        var response = api.delete(TOKEN, KEY);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    @Test
    @DisplayName("exists default stub returns 501 Not Implemented")
    void existsReturnsNotImplemented() throws Exception {
        var response = api.exists(TOKEN, KEY);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    @Test
    @DisplayName("getReference default stub returns 501 Not Implemented")
    void getReferenceReturnsNotImplemented() throws Exception {
        var response = api.getReference(TOKEN, KEY);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    @Test
    @DisplayName("list default stub returns 501 Not Implemented")
    void listReturnsNotImplemented() throws Exception {
        var response = api.list(TOKEN, "profiles/");
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    @Test
    @DisplayName("list with null prefix returns 501 Not Implemented")
    void listNullPrefixReturnsNotImplemented() throws Exception {
        var response = api.list(TOKEN, null);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    @Test
    @DisplayName("SESSION_TOKEN_KEY constant value")
    void sessionTokenKeyConstant() {
        assertEquals("session-token", ImageApi.SESSION_TOKEN_KEY);
    }

    @Test
    @DisplayName("getService returns an ImageService instance")
    void getServiceIsImageService() {
        assertTrue(api.getService() instanceof ImageService);
    }
}
