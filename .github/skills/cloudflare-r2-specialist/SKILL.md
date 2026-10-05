---
name: Cloudflare R2 Specialist Skills
description: Consolidated skill set for the Cloudflare R2 storage integration in commons-services.
applies-to:
  - Cloudflare R2 Specialist
---

# Cloudflare R2 Specialist -- Skill Definition

## 1. Project-Specific Patterns

### S3 client construction (non-negotiable settings)
```java
private S3Client buildS3Client() {
    AwsBasicCredentials credentials = AwsBasicCredentials.create(
        properties.getAccessKey(), properties.getSecretKey());
    S3Configuration serviceConfiguration = S3Configuration.builder()
        .pathStyleAccessEnabled(true)   // REQUIRED: R2 needs path-style URLs
        .chunkedEncodingEnabled(false)  // REQUIRED: chunked encoding causes HTTP 403
        .build();
    return S3Client.builder()
        .endpointOverride(URI.create(properties.getEndpoint()))
        .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .region(Region.of("auto"))      // REQUIRED: ignored by R2 but required by SDK
        .serviceConfiguration(serviceConfiguration)
        .build();
}
```

### Lazy initialisation
```java
// S3Client must be lazily created -- NOT initialised in constructor
private S3Client s3Client;

private S3Client getS3Client() {
    if (s3Client == null) {
        s3Client = buildS3Client();
    }
    return s3Client;
}
```

### Exception hierarchy
```
ImageStorageException (base)
  ImageUploadException
  ImageDownloadException
  ImageDeleteException
  ImageNotFoundException
  ImageValidationException
```

## 2. Naming Conventions
- Client: `CloudflareR2StorageClient`
- Properties: `CloudflareR2Properties` (prefix: cloudflare.r2)
- Service contract: `ImageService`
- Controller contract: `ImageApi`
- TOs: `ImageUploadRequest`, `ImageUploadResponse`, `ImageReferenceResponse` (all records)
- Package: `com.umdc.commons.services.cloudflare`

## 3. Properties Keys
| Java Field | YAML Key | Env Var |
|---|---|---|
| accountId | cloudflare.r2.account-id | CLOUDFLARE_ACCOUNT_ID |
| endpoint | cloudflare.r2.endpoint | CLOUDFLARE_R2_ENDPOINT |
| accessKey | cloudflare.r2.access-key | CLOUDFLARE_R2_ACCESS_KEY (sensitive) |
| secretKey | cloudflare.r2.secret-key | CLOUDFLARE_R2_SECRET_KEY (sensitive) |
| bucketName | cloudflare.r2.bucket-name | CLOUDFLARE_R2_BUCKET_NAME |
| publicUrl | cloudflare.r2.public-url | CLOUDFLARE_R2_PUBLIC_URL |

## 4. Key Files
```
src/main/java/com/umdc/commons/services/cloudflare/r2/client/CloudflareR2StorageClient.java
src/main/java/com/umdc/commons/services/cloudflare/properties/CloudflareR2Properties.java
src/main/java/com/umdc/commons/services/cloudflare/service/ImageService.java
src/main/java/com/umdc/commons/services/cloudflare/controller/ImageApi.java
src/main/java/com/umdc/commons/services/cloudflare/to/
src/main/java/com/umdc/commons/services/cloudflare/exception/
```

## 5. Constraints
- pathStyleAccessEnabled MUST remain true -- NEVER change
- chunkedEncodingEnabled MUST remain false -- NEVER change
- region MUST be Region.of("auto") -- NEVER change
- accessKey and secretKey MUST NEVER be logged at any level
- S3Client MUST be lazily initialised (not in constructor)
- Credentials MUST come from CloudflareR2Properties

## 6. Checklist
- [ ] pathStyleAccessEnabled(true) preserved in S3Client build
- [ ] chunkedEncodingEnabled(false) preserved in S3Client build
- [ ] Region.of("auto") preserved in S3Client build
- [ ] S3Client lazily initialised
- [ ] No accessKey/secretKey in log statements
- [ ] Exception types extend ImageStorageException
- [ ] TOs are Java records
- [ ] Unit tests cover S3 client construction
- [ ] mvn -DskipITs clean verify passes
