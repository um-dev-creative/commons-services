package com.umdc.commons.services.cloudflare.properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("ManagementAuthenticatorProperties")
class ManagementAuthenticatorPropertiesTest {

    private ManagementAuthenticatorProperties props;

    @BeforeEach
    void setUp() {
        props = new ManagementAuthenticatorProperties();
    }

    @Test
    @DisplayName("default constructor initialises fields to null")
    void defaultConstructorFieldsAreNull() {
        assertNull(props.getKeyAlias());
        assertNull(props.getKeystore());
        assertNull(props.getTruststore());
    }

    @Test
    @DisplayName("setKeyAlias / getKeyAlias round-trip")
    void keyAliasRoundTrip() {
        props.setKeyAlias("myAlias");
        assertEquals("myAlias", props.getKeyAlias());
    }

    @Test
    @DisplayName("setKeystore / getKeystore round-trip")
    void keystoreRoundTrip() {
        StoreProperties keystore = new StoreProperties();
        keystore.setLocation("/keystore.p12");
        props.setKeystore(keystore);
        assertEquals("/keystore.p12", props.getKeystore().getLocation());
    }

    @Test
    @DisplayName("setTruststore / getTruststore round-trip")
    void truststoreRoundTrip() {
        StoreProperties truststore = new StoreProperties();
        truststore.setType("JKS");
        props.setTruststore(truststore);
        assertEquals("JKS", props.getTruststore().getType());
    }
}
