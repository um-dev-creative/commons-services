# Component Reference

Detailed reference for every component in `commons-services`.

---

## CrudService

**File:** `src/main/java/com/prx/commons/services/CrudService.java`  
**Type:** Generic interface  
**Pattern:** Default-method stub — all operations return HTTP 501 until overridden.

### Type Parameters

| Parameter | Description |
|---|---|
| `A` | Identifier type (e.g., `Long`, `UUID`, `String`) |
| `T` | Entity/DTO type |

### Methods

| Method | Signature | Default |
|---|---|---|
| create | `ResponseEntity<T> create(T t)` | HTTP 501 |
| update | `ResponseEntity<T> update(A id, T t)` | HTTP 501 |
| delete | `ResponseEntity<T> delete(A id, T t)` | HTTP 501 |
| find | `ResponseEntity<T> find(A id)` | HTTP 501 |
| list | `ResponseEntity<List<T>> list(A... id)` | HTTP 501 |

### Usage

```java
@RestController
@RequestMapping("/products")
public class ProductController implements CrudService<Long, ProductDto> {

    @Override
    public ResponseEntity<ProductDto> find(Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    // create, update, delete, list return 501 until implemented
}
```

---

## ClientRestTemplate

**File:** `src/main/java/com/prx/commons/services/rest/ClientRestTemplate.java`  
**Type:** Base class

Pre-configured `RestTemplate` with:
- `BufferingClientHttpRequestFactory` — allows request/response body to be read multiple times (required for logging interceptors)
- `MappingJackson2HttpMessageConverter` — JSON support limited to `application/json`
- Empty interceptor list by default

### Constructor

```java
public ClientRestTemplate(MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter)
```

### Protected Fields

| Field | Type | Purpose |
|---|---|---|
| `mappingJackson2HttpMessageConverter` | `MappingJackson2HttpMessageConverter` | Jackson converter instance |
| `restTemplate` | `RestTemplate` | Configured RestTemplate instance |

### Usage

```java
@Service
public class MyApiClient extends ClientRestTemplate {

    public MyApiClient(MappingJackson2HttpMessageConverter converter) {
        super(converter);
    }

    public MyDto fetchData(String url) {
        return restTemplate.getForObject(url, MyDto.class);
    }
}
```

---

## Logging Subsystem

The logging subsystem captures HTTP request and response details without modifying application code. It consists of four cooperating components.

### Component Relationships

```
LoggerWebConfigurer
    └── registers → LogInterceptor (HandlerInterceptor, pre-handle)
RequestBodyInterceptor  (@ControllerAdvice, post body-read)
ResponseBodyInterceptor (@ControllerAdvice, pre body-write)
    └── all delegate to → LoggingServiceImp
```

---

### LoggingService

**File:** `src/main/java/com/prx/commons/services/loggers/LoggingService.java`  
**Type:** Interface

| Method | Description |
|---|---|
| `displayRequest(HttpServletRequest, Object body)` | Log HTTP method, URI, query parameters, and body |
| `displayResponse(HttpServletRequest, HttpServletResponse, Object body)` | Log HTTP method, response headers, and body |

---

### LoggingServiceImp

**File:** `src/main/java/com/prx/commons/services/loggers/LoggingServiceImp.java`  
**Type:** `@Service` — Spring-managed bean

Controlled by `prx.logging.trace.enabled` (mapped from `${LOGGING_TRACE_ENABLED}` environment variable). When `false`, both `displayRequest` and `displayResponse` are no-ops.

**Request log format:**
```
REQUEST method = [POST] path = [/api/users] parameters = [{...}] body = [{...}]
```

**Response log format:**
```
RESPONSE method = [POST] ResponseHeaders = [{Content-Type=application/json}] responseBody = [{...}]
```

---

### LogInterceptor

**File:** `src/main/java/com/prx/commons/services/loggers/interceptor/LogInterceptor.java`  
**Type:** `@Component`, implements `HandlerInterceptor`

Fires in `preHandle` for `GET`, `POST`, `PUT`, `DELETE` requests only (method name is case-sensitive). Passes `null` as the body (body is not yet parsed at this stage). Always returns `true` to continue request processing.

---

### RequestBodyInterceptor

**File:** `src/main/java/com/prx/commons/services/loggers/interceptor/RequestBodyInterceptor.java`  
**Type:** `@ControllerAdvice`, extends `RequestBodyAdviceAdapter`

Fires in `afterBodyRead` after Jackson deserializes the request body. At this point the body object is available and is passed to `LoggingService.displayRequest`. Supports all method parameters and converter types (`supports` returns `true` unconditionally).

---

### ResponseBodyInterceptor

**File:** `src/main/java/com/prx/commons/services/loggers/interceptor/ResponseBodyInterceptor.java`  
**Type:** `@ControllerAdvice`, implements `ResponseBodyAdvice<Object>`

Fires in `beforeBodyWrite` before Jackson serializes the response body. Unwraps `ServerHttpRequest`/`ServerHttpResponse` to their servlet equivalents before delegating to `LoggingService.displayResponse`. Returns the body unchanged.

---

### LoggerWebConfigurer

**File:** `src/main/java/com/prx/commons/services/loggers/config/LoggerWebConfigurer.java`  
**Type:** `@Configuration`, implements `WebMvcConfigurer`

Registers `LogInterceptor` globally for all request paths via `InterceptorRegistry`.

---

## Configuration Classes

### JacksonConfig

**File:** `src/main/java/com/prx/commons/services/config/mapper/JacksonConfig.java`  
**Type:** `@Configuration`

Produces an `ObjectMapper` bean registered with `JavaTimeModule`, enabling serialization/deserialization of Java 8+ date/time types (`LocalDate`, `LocalDateTime`, `ZonedDateTime`, `Instant`, etc.).

---

### MapperAppConfig

**File:** `src/main/java/com/prx/commons/services/config/mapper/MapperAppConfig.java`  
**Type:** MapStruct `@MapperConfig`

Base configuration for all MapStruct mappers in the PRX ecosystem.

| Setting | Value | Effect |
|---|---|---|
| `componentModel` | `SPRING` | Mapper beans are managed by Spring IoC |
| `nullValuePropertyMappingStrategy` | `SET_TO_NULL` | Null source properties nullify target properties |
| `unmappedSourcePolicy` | `IGNORE` | No compile error for unmapped source fields |
| `unmappedTargetPolicy` | `IGNORE` | No compile error for unmapped target fields |

**Usage in consuming service:**

```java
@Mapper(config = MapperAppConfig.class)
public interface UserMapper {
    UserDto toDto(User user);
}
```

---

## PrinterUtil

**File:** `src/main/java/com/prx/commons/services/util/PrinterUtil.java`  
**Type:** `@Service`

Conditional logging helper. Reads `${log.debug}` to control default print behavior.

| Method | Description |
|---|---|
| `print(Object, Logger)` | Logs at INFO level if `log.debug=true` |
| `print(Object, Logger, boolean enable)` | Logs at INFO level if `enable=true`, ignores `log.debug` |

---

## Cloudflare Integration

See [cloudflare-r2-integration.md](cloudflare-r2-integration.md) for the full integration guide.

### CloudflareR2StorageClient

**File:** `src/main/java/com/prx/commons/services/cloudflare/r2/client/CloudflareR2StorageClient.java`  
**Type:** `@Component`

| Method | Signature | Description |
|---|---|---|
| `getS3Client()` | `S3Client getS3Client()` | Returns lazily-initialized S3 client |
| `uploadImage` | `String uploadImage(byte[], String objectKey, String contentType)` | Uploads bytes to R2, returns objectKey |
| `downloadImage` | `byte[] downloadImage(String objectKey)` | Downloads bytes from R2 |
| `getPublicUrl` | `String getPublicUrl(String objectKey)` | Prepends `publicUrl` property if set |
| `close` | `void close()` | Closes the underlying S3 client |

---

### ProfileImageService

**File:** `src/main/java/com/prx/commons/services/cloudflare/service/ProfileImageService.java`  
**Type:** Interface  
**Pattern:** Default-method stub — both methods throw `UnsupportedOperationException` until overridden.

| Method | Signature | Default |
|---|---|---|
| save | `ResponseEntity<PostProfileImageResponse> save(String token, UUID applicationId, byte[] image)` | throws `UnsupportedOperationException` |
| getProfileImageReference | `ResponseEntity<GetProfileImageReferenceResponse> getProfileImageReference(String token, UUID applicationId)` | throws `UnsupportedOperationException` |

---

### ProfileImageApi

**File:** `src/main/java/com/prx/commons/services/cloudflare/controller/ProfileImageApi.java`  
**Type:** Spring MVC interface  
**Authentication:** `session-token` request header (constant `SESSION_TOKEN_KEY`)

See [api-reference.md](api-reference.md) for full endpoint documentation.

---

## Properties Classes

See [configuration.md](configuration.md) for all configuration property details.

| Class | Prefix | Purpose |
|---|---|---|
| `CloudflareR2Properties` | `cloudflare.r2` | R2 storage connection settings |
| `SecurityProperties` | `prx.security` | Keystore, truststore, management auth |
| `DiscoveryClientProperties` | `security.discovery.client` | Eureka SSL settings |
| `ManagementAuthenticatorProperties` | nested under `prx.security` | Auth credentials for management |
| `StoreProperties` | nested (reusable) | Keystore/truststore path, password, type |
