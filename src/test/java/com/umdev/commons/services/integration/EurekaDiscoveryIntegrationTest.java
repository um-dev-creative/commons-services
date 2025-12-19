package com.umdev.commons.services.integration;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EurekaDiscoveryIntegrationTest {

    @Test
    void shouldReachEurekaIfConfigured() {
        String eurekaUrl = System.getenv("EUREKA_URL");
        // Skip this test unless an EUREKA_URL is provided (CI will set it when running integration tests)
        Assumptions.assumeTrue(eurekaUrl != null && !eurekaUrl.isBlank(), "EUREKA_URL not set");

        RestTemplate rest = new RestTemplate();
        var response = rest.getForEntity(eurekaUrl + "/apps", String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful(), "Eureka /apps endpoint should respond with 2xx");
    }
}

