/*
 *  @(#)CloudflareR2StorageClient.java
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

package com.umdc.commons.services.cloudflare.r2.client;

import com.umdc.commons.services.cloudflare.properties.CloudflareR2Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;

/**
 * Client for interacting with Cloudflare R2 storage.
 * This component provides methods to upload and download objects from Cloudflare R2.
 */
@Component
public class CloudflareR2StorageClient {

    private static final Logger logger = LoggerFactory.getLogger(CloudflareR2StorageClient.class);
    private final CloudflareR2Properties properties;
    private S3Client s3Client;

    /**
     * Constructor for CloudflareR2StorageClient.
     *
     * @param properties the R2 configuration properties
     */
    public CloudflareR2StorageClient(CloudflareR2Properties properties) {
        this.properties = properties;
    }

    /**
     * Gets the S3 client instance, creating it if necessary.
     *
     * @return the S3 client
     */
    public synchronized S3Client getS3Client() {
        if (s3Client == null) {
            s3Client = buildS3Client();
        }
        return s3Client;
    }

    /**
     * Builds and configures the S3 client for Cloudflare R2.
     *
     * @return configured S3 client
     */
    private S3Client buildS3Client() {
        logger.info("Initializing Cloudflare R2 S3 client");
        logger.info("Endpoint: {}, Bucket: {}", properties.getEndpoint(), properties.getBucketName());

        AwsBasicCredentials credentials = AwsBasicCredentials.create(
            properties.getAccessKey(),
            properties.getSecretKey()
        );

        S3Configuration serviceConfiguration = S3Configuration.builder()
            .pathStyleAccessEnabled(true)
            .chunkedEncodingEnabled(false) // Critical: prevents 403 errors with R2
            .build();

        return S3Client.builder()
            .endpointOverride(URI.create(properties.getEndpoint()))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .region(Region.of("auto")) // Required by SDK but not used by R2
            .serviceConfiguration(serviceConfiguration)
            .build();
    }

    /**
     * Uploads an image to Cloudflare R2.
     *
     * @param imageData the image data as byte array
     * @param objectKey the key (path) for the object in R2
     * @param contentType the MIME type of the image
     * @return the object key of the uploaded image
     */
    public String uploadImage(byte[] imageData, String objectKey, String contentType) {
        logger.info("Uploading image to R2 with key: {}", objectKey);
        
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(properties.getBucketName())
            .key(objectKey)
            .contentType(contentType)
            .build();

        getS3Client().putObject(putObjectRequest, RequestBody.fromBytes(imageData));
        
        logger.info("Image uploaded successfully: {}", objectKey);
        return objectKey;
    }

    /**
     * Downloads an image from Cloudflare R2.
     *
     * @param objectKey the key (path) of the object in R2
     * @return the image data as byte array
     * @throws IOException if download fails
     */
    public byte[] downloadImage(String objectKey) throws IOException {
        logger.info("Downloading image from R2 with key: {}", objectKey);
        
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
            .bucket(properties.getBucketName())
            .key(objectKey)
            .build();

        return getS3Client().getObject(getObjectRequest).readAllBytes();
    }

    /**
     * Generates the public URL for an object.
     *
     * @param objectKey the key of the object
     * @return the public URL
     */
    public String getPublicUrl(String objectKey) {
        if (properties.getPublicUrl() != null && !properties.getPublicUrl().isEmpty()) {
            return properties.getPublicUrl() + "/" + objectKey;
        }
        return objectKey;
    }

    /**
     * Closes the S3 client if it exists.
     */
    public void close() {
        if (s3Client != null) {
            s3Client.close();
        }
    }
}

