package com.umdc.commons.services.cloudflare.r2.client;

import com.umdc.commons.services.cloudflare.properties.CloudflareR2Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("CloudflareR2StorageClient")
@ExtendWith(MockitoExtension.class)
class CloudflareR2StorageClientTest {

    private CloudflareR2Properties properties;

    @BeforeEach
    void setUp() {
        properties = new CloudflareR2Properties();
        properties.setAccountId("test-account");
        properties.setEndpoint("https://test-account.r2.cloudflarestorage.com");
        properties.setAccessKey("test-access-key");
        properties.setSecretKey("test-secret-key");
        properties.setBucketName("test-bucket");
        properties.setPublicUrl("https://pub.example.r2.dev");
    }

    /** Returns a spy with getS3Client() stubbed to the supplied mock — only for tests that call S3. */
    private CloudflareR2StorageClient spyWithMockS3(S3Client mockS3) {
        CloudflareR2StorageClient spy = spy(new CloudflareR2StorageClient(properties));
        doReturn(mockS3).when(spy).getS3Client();
        return spy;
    }

    // =========================================================================
    // uploadImage
    // =========================================================================

    @Test
    @DisplayName("uploadImage calls putObject and returns objectKey")
    void uploadImageCallsPutObjectAndReturnsKey() {
        S3Client mockS3 = mock(S3Client.class);
        CloudflareR2StorageClient client = spyWithMockS3(mockS3);
        when(mockS3.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
                .thenReturn(PutObjectResponse.builder().build());

        String result = client.uploadImage(new byte[]{1, 2, 3}, "profiles/u1/avatar.jpg", "image/jpeg");

        assertEquals("profiles/u1/avatar.jpg", result);
        verify(mockS3).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    @DisplayName("uploadImage delegates putObject with correct bucket and key")
    void uploadImageDelegatesPutObjectWithCorrectBucketAndKey() {
        S3Client mockS3 = mock(S3Client.class);
        CloudflareR2StorageClient client = spyWithMockS3(mockS3);
        when(mockS3.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
                .thenReturn(PutObjectResponse.builder().build());

        client.uploadImage(new byte[]{1}, "key.jpg", "image/png");

        verify(mockS3).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    // =========================================================================
    // downloadImage
    // =========================================================================

    @Test
    @DisplayName("downloadImage calls getObject and returns bytes")
    void downloadImageReturnsBytes() throws IOException {
        S3Client mockS3 = mock(S3Client.class);
        CloudflareR2StorageClient client = spyWithMockS3(mockS3);
        byte[] expected = new byte[]{10, 20, 30};

        @SuppressWarnings("unchecked")
        ResponseInputStream<GetObjectResponse> responseStream = mock(ResponseInputStream.class);
        when(responseStream.readAllBytes()).thenReturn(expected);
        when(mockS3.getObject(any(GetObjectRequest.class))).thenReturn(responseStream);

        byte[] result = client.downloadImage("profiles/u1/avatar.jpg");

        assertArrayEquals(expected, result);
        verify(mockS3).getObject(any(GetObjectRequest.class));
    }

    @Test
    @DisplayName("downloadImage propagates IOException from stream read")
    void downloadImagePropagatesIOException() throws IOException {
        S3Client mockS3 = mock(S3Client.class);
        CloudflareR2StorageClient client = spyWithMockS3(mockS3);

        @SuppressWarnings("unchecked")
        ResponseInputStream<GetObjectResponse> responseStream = mock(ResponseInputStream.class);
        when(responseStream.readAllBytes()).thenThrow(new IOException("read error"));
        when(mockS3.getObject(any(GetObjectRequest.class))).thenReturn(responseStream);

        assertThrows(IOException.class, () -> client.downloadImage("profiles/u1/avatar.jpg"));
    }

    // =========================================================================
    // getPublicUrl
    // =========================================================================

    @Test
    @DisplayName("getPublicUrl returns publicUrl + objectKey when publicUrl is configured")
    void getPublicUrlWithConfiguredPublicUrl() {
        CloudflareR2StorageClient client = new CloudflareR2StorageClient(properties);
        assertEquals("https://pub.example.r2.dev/profiles/u1/avatar.jpg",
                client.getPublicUrl("profiles/u1/avatar.jpg"));
    }

    @Test
    @DisplayName("getPublicUrl returns objectKey as-is when publicUrl is null")
    void getPublicUrlWhenPublicUrlIsNull() {
        properties.setPublicUrl(null);
        CloudflareR2StorageClient client = new CloudflareR2StorageClient(properties);
        assertEquals("profiles/u1/avatar.jpg", client.getPublicUrl("profiles/u1/avatar.jpg"));
    }

    @Test
    @DisplayName("getPublicUrl returns objectKey as-is when publicUrl is empty")
    void getPublicUrlWhenPublicUrlIsEmpty() {
        properties.setPublicUrl("");
        CloudflareR2StorageClient client = new CloudflareR2StorageClient(properties);
        assertEquals("profiles/u1/avatar.jpg", client.getPublicUrl("profiles/u1/avatar.jpg"));
    }

    // =========================================================================
    // close
    // =========================================================================

    @Test
    @DisplayName("close is a no-op when S3Client has not been initialized")
    void closeWhenNotInitialized() {
        CloudflareR2StorageClient client = new CloudflareR2StorageClient(properties);
        assertDoesNotThrow(client::close);
    }

    @Test
    @DisplayName("close calls S3Client.close when client is initialized")
    void closeCallsS3ClientClose() throws Exception {
        S3Client mockToClose = mock(S3Client.class);
        CloudflareR2StorageClient client = new CloudflareR2StorageClient(properties);

        Field field = CloudflareR2StorageClient.class.getDeclaredField("s3Client");
        field.setAccessible(true);
        field.set(client, mockToClose);

        client.close();

        verify(mockToClose).close();
    }

    // =========================================================================
    // getS3Client — lazy initialization
    // =========================================================================

    @Test
    @DisplayName("getS3Client returns a non-null S3Client on first call")
    void getS3ClientReturnsNonNull() {
        CloudflareR2StorageClient client = new CloudflareR2StorageClient(properties);
        S3Client s3 = client.getS3Client();
        assertNotNull(s3);
        client.close();
    }

    @Test
    @DisplayName("getS3Client returns same instance on subsequent calls")
    void getS3ClientReturnsSameInstance() {
        CloudflareR2StorageClient client = new CloudflareR2StorageClient(properties);
        S3Client first = client.getS3Client();
        S3Client second = client.getS3Client();
        assertSame(first, second);
        client.close();
    }
}
