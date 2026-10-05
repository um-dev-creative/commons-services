package com.umdc.commons.services.cloudflare.to;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ImageUploadRequest")
class ImageUploadRequestTest {

    private static final byte[] VALID_DATA = new byte[]{1, 2, 3};
    private static final String JPEG = "image/jpeg";

    // =========================================================================
    // Validation — null / empty guards
    // =========================================================================

    @Test
    @DisplayName("null data throws NullPointerException")
    void nullDataThrows() {
        assertThrows(NullPointerException.class,
                () -> new ImageUploadRequest(null, null, JPEG, null));
    }

    @Test
    @DisplayName("empty data throws IllegalArgumentException")
    void emptyDataThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new ImageUploadRequest(null, new byte[0], JPEG, null));
    }

    @Test
    @DisplayName("null contentType throws NullPointerException")
    void nullContentTypeThrows() {
        assertThrows(NullPointerException.class,
                () -> new ImageUploadRequest(null, VALID_DATA, null, null));
    }

    // =========================================================================
    // Construction — direct constructor
    // =========================================================================

    @Test
    @DisplayName("valid constructor sets all fields")
    void validConstructorSetsFields() {
        Map<String, String> meta = Map.of("userId", "u1");
        ImageUploadRequest req = new ImageUploadRequest("key/avatar.jpg", VALID_DATA, JPEG, meta);

        assertEquals("key/avatar.jpg", req.objectKey());
        assertEquals(JPEG, req.contentType());
        assertEquals("u1", req.metadata().get("userId"));
    }

    @Test
    @DisplayName("null objectKey is allowed")
    void nullObjectKeyAllowed() {
        ImageUploadRequest req = new ImageUploadRequest(null, VALID_DATA, JPEG, null);
        assertNull(req.objectKey());
    }

    @Test
    @DisplayName("null metadata becomes empty map")
    void nullMetadataBecomeEmptyMap() {
        ImageUploadRequest req = new ImageUploadRequest(null, VALID_DATA, JPEG, null);
        assertNotNull(req.metadata());
        assertTrue(req.metadata().isEmpty());
    }

    @Test
    @DisplayName("metadata map is unmodifiable")
    void metadataIsUnmodifiable() {
        Map<String, String> mutable = new HashMap<>();
        mutable.put("k", "v");
        ImageUploadRequest req = new ImageUploadRequest(null, VALID_DATA, JPEG, mutable);
        var map = req.metadata();
        assertThrows(UnsupportedOperationException.class, () -> map.put("x", "y"));
    }

    // =========================================================================
    // Defensive copy of byte array
    // =========================================================================

    @Test
    @DisplayName("data is defensively copied on construction")
    void dataIsDefensivelyCopied() {
        byte[] original = new byte[]{10, 20, 30};
        ImageUploadRequest req = new ImageUploadRequest(null, original, JPEG, null);
        original[0] = 99;
        assertEquals(10, req.data()[0]);
    }

    @Test
    @DisplayName("data array is a different reference than the input")
    void dataArrayIsCopy() {
        byte[] original = new byte[]{1, 2, 3};
        ImageUploadRequest req = new ImageUploadRequest(null, original, JPEG, null);
        assertNotSame(original, req.data());
    }

    // =========================================================================
    // Factory methods
    // =========================================================================

    @Test
    @DisplayName("of(data, contentType) sets null objectKey")
    void factoryOfDataContentType() {
        ImageUploadRequest req = ImageUploadRequest.of(VALID_DATA, JPEG);
        assertNull(req.objectKey());
        assertArrayEquals(VALID_DATA, req.data());
        assertEquals(JPEG, req.contentType());
        assertTrue(req.metadata().isEmpty());
    }

    @Test
    @DisplayName("of(objectKey, data, contentType) sets objectKey")
    void factoryOfKeyDataContentType() {
        ImageUploadRequest req = ImageUploadRequest.of("profiles/u1/a.jpg", VALID_DATA, JPEG);
        assertEquals("profiles/u1/a.jpg", req.objectKey());
        assertArrayEquals(VALID_DATA, req.data());
        assertEquals(JPEG, req.contentType());
    }

    @Test
    @DisplayName("of(objectKey, data, contentType, metadata) sets all fields")
    void factoryOfKeyDataContentTypeMetadata() {
        Map<String, String> meta = Map.of("source", "web");
        ImageUploadRequest req = ImageUploadRequest.of("k", VALID_DATA, JPEG, meta);
        assertEquals("k", req.objectKey());
        assertEquals("web", req.metadata().get("source"));
    }

    // =========================================================================
    // equals / hashCode — content-based for byte[]
    // =========================================================================

    @Test
    @DisplayName("equals returns true for same byte content in different array instances")
    void equalsContentBased() {
        byte[] a = new byte[]{1, 2, 3};
        byte[] b = new byte[]{1, 2, 3};
        ImageUploadRequest r1 = new ImageUploadRequest("key", a, JPEG, null);
        ImageUploadRequest r2 = new ImageUploadRequest("key", b, JPEG, null);
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    @DisplayName("equals returns false when byte content differs")
    void equalsReturnsFalseForDifferentData() {
        ImageUploadRequest r1 = new ImageUploadRequest("key", new byte[]{1, 2, 3}, JPEG, null);
        ImageUploadRequest r2 = new ImageUploadRequest("key", new byte[]{9, 9, 9}, JPEG, null);
        assertNotNull(r1);
        assertNotNull(r2);
        assertNotSame(r1, r2);
        assertNotEquals(r1, r2);
    }

    @Test
    @DisplayName("toString includes objectKey, contentType, and metadata")
    void toStringIncludesFields() {
        ImageUploadRequest req = new ImageUploadRequest("my/key", VALID_DATA, JPEG, null);
        String str = req.toString();
        assertTrue(str.contains("my/key"));
        assertTrue(str.contains(JPEG));
    }
}
