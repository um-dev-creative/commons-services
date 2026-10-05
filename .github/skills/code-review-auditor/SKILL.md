---
name: Code Review Auditor Skills
description: Consolidated skill set for code review in commons-services shared library context.
applies-to:
  - Code Review Auditor
---

# Code Review Auditor -- Skill Definition

## 1. Review Signals by Area

### Interface Contract Safety
- Any change to CrudService, ImageApi, ImageService, or LoggingService method signatures -> CRITICAL
- New method added to interface without a default implementation -> CRITICAL
- ImageApi returning raw integer (200, 404, 501) instead of HttpStatusUtil constant -> HIGH

### Logging Pipeline Integrity
- Log statement added directly in a controller or endpoint (bypassing LoggingService) -> HIGH
- LogInterceptor returning false from preHandle -> CRITICAL
- LogInterceptor firing for HTTP methods other than GET, POST, PUT, DELETE -> MEDIUM

### R2 S3 Client Settings
- pathStyleAccessEnabled changed from true -> CRITICAL
- chunkedEncodingEnabled changed from false -> CRITICAL
- region changed from Region.of("auto") -> CRITICAL

### Credential Safety
- accessKey or secretKey logged at any level -> CRITICAL
- Credentials hardcoded in any .java, .yml, or .properties file -> CRITICAL

### Code Style
- Lombok annotation introduced -> HIGH
- @Autowired field injection introduced -> HIGH
- System.out.println or System.err -> MEDIUM
- Missing constructor injection -> MEDIUM

### Coverage
- New class with no test coverage -> HIGH
- Existing test deleted without replacement -> HIGH

## 2. Key Reference Files
```
src/main/java/com/umdc/commons/services/CrudService.java
src/main/java/com/umdc/commons/services/cloudflare/controller/ImageApi.java
src/main/java/com/umdc/commons/services/cloudflare/service/ImageService.java
src/main/java/com/umdc/commons/services/cloudflare/r2/client/CloudflareR2StorageClient.java
src/main/java/com/umdc/commons/services/loggers/interceptor/LogInterceptor.java
```

## 3. Constraints
- Read-only except for adding test stubs (CREATE_TESTS grant only)
- Never modify production source code
- Never approve changes that reduce coverage below thresholds

## 4. Checklist
- [ ] No published interface method signatures changed
- [ ] No abstract-only methods added to shared interfaces
- [ ] LoggingService chain intact
- [ ] R2 S3 client settings unchanged
- [ ] No credentials in source files
- [ ] No Lombok, no field injection
- [ ] New code has tests
- [ ] mvn -DskipITs clean verify passes
