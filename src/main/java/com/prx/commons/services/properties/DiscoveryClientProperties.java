/*
 * @(#)DiscoveryClientProperties.java
 *
 * Copyright (c) Luis Antonio Mata Mata. All rights reserved.
 *
 * All rights to this product are owned by Luis Antonio Mata Mata and may only
 * be used under the terms of its associated license document. You may NOT
 * copy, modify, sublicense, or distribute this source file or portions of
 * it unless previously authorized in writing by Luis Antonio Mata Mata.
 * In any event, this notice and the above copyright must always be included
 * verbatim with this file.
 */

package com.prx.commons.services.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/// DiscoveryClientProperties.
///
/// @author <a href='mailto:luis.antonio.mata@gmail.com'>Luis Antonio Mata.</a>
/// @version 0.0.1
@Component
@ConfigurationProperties(prefix = "security.discovery.client")
public class DiscoveryClientProperties {

    private String name;
    private String trustStoreFile;
    private String trustStorePassword;
    private Integer maxTotalConnections;
    private Integer maxConnectionsPerHost;

    /// Default constructor.
    public DiscoveryClientProperties() {
        //Default constructor.
    }

    ///  Return the name of the client.
    ///  @return the name of the client
    public String getName() {
        return name;
    }

    ///  Set the name of the client.
    /// @param name the name of the client
    public void setName(String name) {
        this.name = name;
    }

    ///  Return the trust store file.
    /// @return the trust store file
    public String getTrustStoreFile() {
        return trustStoreFile;
    }

    ///  Set the trust store file.
    /// @param trustStoreFile the trust store file
    public void setTrustStoreFile(String trustStoreFile) {
        this.trustStoreFile = trustStoreFile;
    }

    ///  Return the trust store password.
    /// @return the trust store password
    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    ///  Set the trust store password.
    /// @param trustStorePassword the trust store password
    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    ///  Return the maximum total connections.
    /// @return the maximum total connections
    public Integer getMaxTotalConnections() {
        return maxTotalConnections;
    }

    ///  Set the maximum total connections.
    /// @param maxTotalConnections the maximum total connections
    public void setMaxTotalConnections(Integer maxTotalConnections) {
        this.maxTotalConnections = maxTotalConnections;
    }

    ///  Return the maximum connections per host.
    /// @return the maximum connections per host
    public Integer getMaxConnectionsPerHost() {
        return maxConnectionsPerHost;
    }

    ///  Set the maximum connections per host.
    /// @param maxConnectionsPerHost the maximum connections per host
    public void setMaxConnectionsPerHost(Integer maxConnectionsPerHost) {
        this.maxConnectionsPerHost = maxConnectionsPerHost;
    }
}
