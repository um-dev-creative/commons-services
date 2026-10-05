package com.umdc.commons.services.cloudflare.properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("StoreProperties")
class StorePropertiesTest {

    private StoreProperties props;

    @BeforeEach
    void setUp() {
        props = new StoreProperties();
    }

    @Test
    @DisplayName("default constructor initialises fields to null")
    void defaultConstructorFieldsAreNull() {
        assertNull(props.getLocation());
        assertNull(props.getPassword());
        assertNull(props.getType());
    }

    @Test
    @DisplayName("setLocation / getLocation round-trip")
    void locationRoundTrip() {
        props.setLocation("/etc/ssl/keystore.p12");
        assertEquals("/etc/ssl/keystore.p12", props.getLocation());
    }

    @Test
    @DisplayName("setPassword / getPassword round-trip")
    void passwordRoundTrip() {
        props.setPassword("s3cr3t");
        assertEquals("s3cr3t", props.getPassword());
    }

    @Test
    @DisplayName("setType / getType round-trip")
    void typeRoundTrip() {
        props.setType("PKCS12");
        assertEquals("PKCS12", props.getType());
    }
}
