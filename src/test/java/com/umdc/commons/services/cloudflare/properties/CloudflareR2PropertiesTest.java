package com.umdc.commons.services.cloudflare.properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("CloudflareR2Properties")
class CloudflareR2PropertiesTest {

    private CloudflareR2Properties props;

    @BeforeEach
    void setUp() {
        props = new CloudflareR2Properties();
    }

    @Test
    @DisplayName("default constructor initialises fields to null")
    void defaultConstructorFieldsAreNull() {
        assertNull(props.getAccountId());
        assertNull(props.getEndpoint());
        assertNull(props.getAccessKey());
        assertNull(props.getSecretKey());
        assertNull(props.getBucketName());
        assertNull(props.getPublicUrl());
    }

    @Test
    @DisplayName("setAccountId / getAccountId round-trip")
    void accountIdRoundTrip() {
        props.setAccountId("abc123");
        assertEquals("abc123", props.getAccountId());
    }

    @Test
    @DisplayName("setEndpoint / getEndpoint round-trip")
    void endpointRoundTrip() {
        props.setEndpoint("https://abc123.r2.cloudflarestorage.com");
        assertEquals("https://abc123.r2.cloudflarestorage.com", props.getEndpoint());
    }

    @Test
    @DisplayName("setAccessKey / getAccessKey round-trip")
    void accessKeyRoundTrip() {
        props.setAccessKey("access-key");
        assertEquals("access-key", props.getAccessKey());
    }

    @Test
    @DisplayName("setSecretKey / getSecretKey round-trip")
    void secretKeyRoundTrip() {
        props.setSecretKey("secret-key");
        assertEquals("secret-key", props.getSecretKey());
    }

    @Test
    @DisplayName("setBucketName / getBucketName round-trip")
    void bucketNameRoundTrip() {
        props.setBucketName("my-bucket");
        assertEquals("my-bucket", props.getBucketName());
    }

    @Test
    @DisplayName("setPublicUrl / getPublicUrl round-trip")
    void publicUrlRoundTrip() {
        props.setPublicUrl("https://pub.example.r2.dev");
        assertEquals("https://pub.example.r2.dev", props.getPublicUrl());
    }
}
