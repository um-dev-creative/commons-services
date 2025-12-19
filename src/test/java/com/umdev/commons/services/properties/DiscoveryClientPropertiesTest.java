package com.umdev.commons.services.properties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DiscoveryClientPropertiesTest {

    @Test
    @DisplayName("Get and set name")
    void getNameAndSetName() {
        DiscoveryClientProperties properties = new DiscoveryClientProperties();
        properties.setName("TestClient");
        assertEquals("TestClient", properties.getName());
    }

    @Test
    @DisplayName("Get and set trust store file")
    void getTrustStoreFileAndSetTrustStoreFile() {
        DiscoveryClientProperties properties = new DiscoveryClientProperties();
        properties.setTrustStoreFile("truststore.jks");
        assertEquals("truststore.jks", properties.getTrustStoreFile());
    }

    @Test
    @DisplayName("Get and set trust store password")
    void getTrustStorePasswordAndSetTrustStorePassword() {
        DiscoveryClientProperties properties = new DiscoveryClientProperties();
        properties.setTrustStorePassword("password");
        assertEquals("password", properties.getTrustStorePassword());
    }

    @Test
    @DisplayName("Get and set max total connections")
    void getMaxTotalConnectionsAndSetMaxTotalConnections() {
        DiscoveryClientProperties properties = new DiscoveryClientProperties();
        properties.setMaxTotalConnections(100);
        assertEquals(100, properties.getMaxTotalConnections());
    }

    @Test
    @DisplayName("Get and set max connections per host")
    void getMaxConnectionsPerHostAndSetMaxConnectionsPerHost() {
        DiscoveryClientProperties properties = new DiscoveryClientProperties();
        properties.setMaxConnectionsPerHost(10);
        assertEquals(10, properties.getMaxConnectionsPerHost());
    }

    @Test
    @DisplayName("Default values")
    void defaultValues() {
        DiscoveryClientProperties properties = new DiscoveryClientProperties();
        assertNull(properties.getName());
        assertNull(properties.getTrustStoreFile());
        assertNull(properties.getTrustStorePassword());
        assertNull(properties.getMaxTotalConnections());
        assertNull(properties.getMaxConnectionsPerHost());
    }
}
