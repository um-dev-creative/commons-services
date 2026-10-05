---
name: Contracts Architect Skills
description: Consolidated skill set for designing and evolving shared Java interfaces in commons-services.
applies-to:
  - Contracts Architect
---

# Contracts Architect -- Skill Definition

## 1. Project-Specific Patterns

### Default method stubs
```java
// CrudService -- all methods default to HTTP 501
default ResponseEntity<T> create(T t) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
}

// ImageService -- operations throw UnsupportedOperationException
default ResponseEntity<ImageUploadResponse> save(
        String token, UUID applicationId, byte[] image) throws Exception {
    throw new UnsupportedOperationException("Not implemented");
}

// ImageApi -- returns HttpStatusUtil constant
default ResponseEntity<ImageUploadResponse> uploadImage(
        String token, UUID applicationId, byte[] imageData) {
    return ResponseEntity.status(HttpStatusUtil.NOT_IMPLEMENTED).body(null);
}
```

### Java record transfer objects
```java
// All TOs are records -- no Lombok, no mutable fields
public record ImageUploadResponse(String imageId, String publicUrl) {}
public record ImageUploadRequest(byte[] imageData, String contentType) {}
public record ImageReferenceResponse(String imageId, String referenceUrl) {}
```

## 2. Naming Conventions
- Interfaces: `CrudService`, `ImageApi`, `ImageService`, `LoggingService`
- Records (TOs): `ImageUploadRequest`, `ImageUploadResponse`, `ImageReferenceResponse`
- Tests: `CrudServiceTest`, `ImageApiTest`, `ImageServiceTest`
- Package: `com.umdc.commons.services` (NOT `com.prx.commons.services`)

## 3. HTTP Status Handling
- Use `HttpStatusUtil` constants from `com.prx:prx-commons` dependency
- Never use raw integers (200, 404, 501) in ImageApi
- `CrudService` uses `HttpStatus.NOT_IMPLEMENTED` (Spring enum is allowed there)

## 4. Key Files
```
src/main/java/com/umdc/commons/services/CrudService.java
src/main/java/com/umdc/commons/services/cloudflare/controller/ImageApi.java
src/main/java/com/umdc/commons/services/cloudflare/service/ImageService.java
src/main/java/com/umdc/commons/services/loggers/LoggingService.java
src/main/java/com/umdc/commons/services/cloudflare/to/ImageUploadRequest.java
src/main/java/com/umdc/commons/services/cloudflare/to/ImageUploadResponse.java
src/main/java/com/umdc/commons/services/cloudflare/to/ImageReferenceResponse.java
```

## 5. Constraints
- NEVER change parameter types, return types, or names of published interface methods
- NEVER add abstract methods to published interfaces -- always provide a default
- NEVER introduce Lombok on any class
- NEVER use @Autowired field injection
- NEVER use raw HTTP status integer literals in ImageApi

## 6. Checklist
- [ ] All new interface methods have a safe default implementation
- [ ] All new TOs are Java records
- [ ] HttpStatusUtil used for all ImageApi status codes
- [ ] No existing public method signatures changed
- [ ] Unit tests cover all new default-method behaviors
- [ ] mvn -DskipITs clean verify passes
