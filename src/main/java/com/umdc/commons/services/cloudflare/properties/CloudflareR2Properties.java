/*
 *  @(#)CloudflareR2Properties.java
 *
 *  Copyright (c) Luis Antonio Mata Mata. All rights reserved.
 *
 *   All rights to this product are owned by Luis Antonio Mata Mata and may only
 *  be used under the terms of its associated license document. You may NOT
 *  copy, modify, sublicense, or distribute this source file or portions of
 *  it unless previously authorized in writing by Luis Antonio Mata Mata.
 *  In any event, this notice and the above copyright must always be included
 *  verbatim with this file.
 */

package com.umdc.commons.services.cloudflare.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Properties for Cloudflare R2 storage configuration.
 * This class holds the configuration properties for Cloudflare R2, including account ID, endpoint, credentials, and bucket name.
 */
@Configuration
@ConfigurationProperties(prefix = "cloudflare.r2")
public class CloudflareR2Properties {
    private String accountId;
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String publicUrl;

    /**
     * Default constructor.
     * Creates a new instance of CloudflareR2Properties.
     */
    public CloudflareR2Properties() {
        // Default constructor
    }

    /**
     * Gets the account ID.
     *
     * @return the account ID
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Sets the account ID.
     *
     * @param accountId the account ID to set
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * Gets the R2 endpoint URL.
     *
     * @return the endpoint URL
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Sets the R2 endpoint URL.
     *
     * @param endpoint the endpoint URL to set
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Gets the access key.
     *
     * @return the access key
     */
    public String getAccessKey() {
        return accessKey;
    }

    /**
     * Sets the access key.
     *
     * @param accessKey the access key to set
     */
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    /**
     * Gets the secret key.
     *
     * @return the secret key
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Sets the secret key.
     *
     * @param secretKey the secret key to set
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Gets the bucket name.
     *
     * @return the bucket name
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * Sets the bucket name.
     *
     * @param bucketName the bucket name to set
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * Gets the public URL for accessing stored objects.
     *
     * @return the public URL
     */
    public String getPublicUrl() {
        return publicUrl;
    }

    /**
     * Sets the public URL for accessing stored objects.
     *
     * @param publicUrl the public URL to set
     */
    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }
}

