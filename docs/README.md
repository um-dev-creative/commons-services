# commons-services — Technical Documentation

Shared Java library providing reusable service components, REST API contracts, HTTP interceptors, configuration helpers, and Cloudflare R2 integration for PRX microservices.

**Artifact:** `com.prx:commons-services:0.0.1`  
**Java:** 21 | **Spring Boot:** 3.5.8 | **Spring Cloud:** 2025.0.1

---

## Documents

| Document | Description |
|---|---|
| [architecture.md](architecture.md) | High-level architecture, module structure, component interaction diagrams, and CI/CD overview |
| [components.md](components.md) | Detailed reference for every component: interfaces, classes, methods, and usage examples |
| [api-reference.md](api-reference.md) | REST API contract for the Profile Image API — endpoints, request/response formats, OpenAPI annotations |
| [configuration.md](configuration.md) | All `@ConfigurationProperties` bindings, required properties, and environment variable reference |
| [cloudflare-r2-integration.md](cloudflare-r2-integration.md) | Cloudflare R2 storage integration guide — setup, client usage, object key conventions, error handling |
| [logging-subsystem.md](logging-subsystem.md) | HTTP request/response logging — how it works, enabling/disabling, customization |
| [development-guidelines.md](development-guidelines.md) | Build commands, coding conventions, PMD rules, test conventions, publishing |
| [diagrams.md](diagrams.md) | All Mermaid architecture diagrams in one place |

---

## Quick Reference

### Build

```bash
mvn -DskipITs clean verify        # standard build
mvn test -Dtest=ClassName         # single test class
```

### Enable HTTP Trace Logging

```yaml
prx:
  logging:
    trace:
      enabled: true
```

### Use as a Dependency

```xml
<dependency>
    <groupId>com.prx</groupId>
    <artifactId>commons-services</artifactId>
    <version>0.0.1</version>
</dependency>
```

### Implement a CRUD Controller

```java
@RestController
public class MyController implements CrudService<Long, MyDto> {
    @Override
    public ResponseEntity<MyDto> find(Long id) { ... }
}
```

### Implement Profile Image Handling

```java
@RestController
@RequestMapping("/v1/profile-images")
public class ProfileImageController implements ProfileImageApi {
    @Override
    public ResponseEntity<PostProfileImageResponse> uploadProfileImage(
            String token, UUID applicationId, byte[] image) throws Exception { ... }
}
```
