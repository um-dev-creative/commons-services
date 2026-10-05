# Architecture Overview

## Purpose

`commons-services` is a **shared Java library** (`com.prx:commons-services:0.0.1`) that provides reusable service components, REST API contracts, interceptors, configuration helpers, and cloud storage integration for PRX microservices. It is **not a runnable application** — it is consumed as a Maven dependency by downstream services.

---

## Technology Stack

| Layer | Technology | Version |
|---|---|---|
| Language | Java | 21 |
| Framework | Spring Boot | 3.5.8 |
| Cloud | Spring Cloud | 2025.0.1 |
| Object Mapping | MapStruct | 1.6.3 |
| Cloud Storage | AWS SDK v2 (S3-compatible) | 2.21.0 |
| API Documentation | SpringDoc OpenAPI | 1.8.0 |
| Logging | Log4j2 | 2.25.3 |
| JSON | Jackson + Gson | managed |
| Testing | JUnit Jupiter + Mockito | 5.14.1 / 5.21.0 |
| Coverage | JaCoCo | 0.8.14 |
| Static Analysis | PMD | 3.28.0 |

---

## Module Structure

```
com.prx.commons.services
├── CrudService<A,T>                      # Generic CRUD contract
├── config/
│   └── mapper/
│       ├── JacksonConfig                 # ObjectMapper with JavaTimeModule
│       └── MapperAppConfig               # Base MapStruct configuration
├── loggers/
│   ├── LoggingService                    # HTTP logging contract
│   ├── LoggingServiceImp                 # Trace-enabled implementation
│   ├── config/
│   │   └── LoggerWebConfigurer           # Registers interceptors via WebMvcConfigurer
│   └── interceptor/
│       ├── LogInterceptor                # HandlerInterceptor (pre-handle)
│       ├── RequestBodyInterceptor        # RequestBodyAdviceAdapter (post-read)
│       └── ResponseBodyInterceptor       # ResponseBodyAdvice (pre-write)
├── properties/
│   ├── DiscoveryClientProperties         # Eureka client SSL config
│   └── SecurityProperties               # Keystore, truststore, management auth
├── rest/
│   └── ClientRestTemplate               # Buffered RestTemplate wrapper
├── util/
│   └── PrinterUtil                      # Conditional logging utility
└── cloudflare/
    ├── controller/
    │   └── ProfileImageApi              # REST interface (Spring MVC)
    ├── properties/
    │   ├── CloudflareR2Properties       # R2 storage config
    │   ├── ManagementAuthenticatorProperties
    │   └── StoreProperties
    ├── r2/client/
    │   └── CloudflareR2StorageClient    # AWS SDK S3 client for R2
    ├── service/
    │   └── ProfileImageService          # Profile image service contract
    └── to/
        ├── PostProfileImageResponse     # Upload response record
        └── GetProfileImageReferenceResponse # Reference response record
```

---

## Core Design Pattern

The library uses an **interface-with-default-methods** pattern throughout. Every contract exposes default method implementations that return HTTP 501 Not Implemented (or throw `UnsupportedOperationException`) — acting as safe stubs. Consuming microservices implement the interface and override only the operations they support.

```mermaid
flowchart TD
    I["Interface · commons-services library\ndefault method → HTTP 501 or UnsupportedOperationException"]
    C["Concrete class · consuming microservice\n@Override → actual business logic"]
    I -->|"implements"| C
```

Interfaces following this pattern:
- `CrudService<A, T>`
- `ProfileImageService`
- `ProfileImageApi`
- `LoggingService`

---

## Component Interaction Diagram

```mermaid
graph TD
    subgraph lib["commons-services library"]
        CrudService["CrudService(A,T)\nCRUD contract"]
        ClientRest["ClientRestTemplate\nBuffered RestTemplate wrapper"]

        subgraph log["Logging Subsystem"]
            LS["LoggingService\ninterface"]
            LSI["LoggingServiceImp\n@Service"]
            LWC["LoggerWebConfigurer\n@Configuration · WebMvcConfigurer"]
            LI["LogInterceptor\n@Component · HandlerInterceptor"]
            RqBI["RequestBodyInterceptor\n@ControllerAdvice"]
            RsBI["ResponseBodyInterceptor\n@ControllerAdvice"]
        end

        subgraph cf["Cloudflare Integration"]
            PIA["ProfileImageApi\nREST interface"]
            PIS["ProfileImageService\nservice contract"]
            R2Client["CloudflareR2StorageClient\n@Component"]
            R2Props["CloudflareR2Properties\n@ConfigurationProperties"]
        end

        subgraph cfg["Configuration"]
            SecProps["SecurityProperties\nprx.security.*"]
            DiscProps["DiscoveryClientProperties\nsecurity.discovery.client.*"]
            JacksonConf["JacksonConfig\nObjectMapper bean"]
            MapperConf["MapperAppConfig\nMapStruct base"]
        end
    end

    subgraph svc["Consuming Microservice"]
        Controller["@RestController\nimplements ProfileImageApi"]
        ServiceImpl["@Service\nimplements ProfileImageService"]
        MapperImpl["@Mapper\nconfig = MapperAppConfig"]
    end

    LSI -.->|implements| LS
    LWC -->|registers| LI
    LI -->|calls| LS
    RqBI -->|calls| LS
    RsBI -->|calls| LS
    R2Client -->|reads| R2Props
    Controller -->|implements| PIA
    ServiceImpl -->|implements| PIS
    ServiceImpl -->|uses| R2Client
    MapperImpl -->|config| MapperConf
```

---

## HTTP Request Lifecycle (Logging)

```mermaid
sequenceDiagram
    participant Client
    participant LogInterceptor
    participant RequestBodyInterceptor
    participant Controller
    participant ResponseBodyInterceptor
    participant LoggingServiceImp

    Client->>LogInterceptor: HTTP Request (preHandle)
    alt method is GET / POST / PUT / DELETE
        LogInterceptor->>LoggingServiceImp: displayRequest(request, null)
        Note over LoggingServiceImp: Logs method + URI only
    end
    LogInterceptor-->>Client: return true (continue)

    RequestBodyInterceptor->>LoggingServiceImp: displayRequest(request, body)
    Note over LoggingServiceImp: Logs method + URI + body
    RequestBodyInterceptor->>Controller: afterBodyRead → body

    Controller-->>ResponseBodyInterceptor: ResponseEntity (beforeBodyWrite)
    ResponseBodyInterceptor->>LoggingServiceImp: displayResponse(request, response, body)
    Note over LoggingServiceImp: Logs method + headers + body
    ResponseBodyInterceptor-->>Client: HTTP Response
```

---

## Profile Image Upload Flow

```mermaid
sequenceDiagram
    participant Client
    participant ProfileImageApi
    participant ProfileImageService
    participant CloudflareR2StorageClient
    participant CloudflareR2

    Client->>ProfileImageApi: POST /application/{applicationId}\nsession-token header + image bytes
    ProfileImageApi->>ProfileImageService: save(token, applicationId, image)
    ProfileImageService->>CloudflareR2StorageClient: uploadImage(imageData, objectKey, contentType)
    CloudflareR2StorageClient->>CloudflareR2: S3 PutObject (path-style, no chunked encoding)
    CloudflareR2-->>CloudflareR2StorageClient: success
    CloudflareR2StorageClient-->>ProfileImageService: objectKey
    ProfileImageService-->>ProfileImageApi: ResponseEntity<PostProfileImageResponse>
    ProfileImageApi-->>Client: 201 Created { ref: "objectKey" }
```

---

## Deployment Model

This library is not deployed independently. It is resolved as a Maven dependency from the Repsy private repository:

```xml
<dependency>
    <groupId>com.prx</groupId>
    <artifactId>commons-services</artifactId>
    <version>0.0.1</version>
</dependency>
```

Repository configuration required in consuming service `pom.xml`:

```xml
<repository>
    <id>repsy</id>
    <name>PRX Private Maven Repository</name>
    <url>https://repo.repsy.io/mvn/lmata/prx</url>
</repository>
```

Publishing requires `REPSY_ACCOUNT_USER` and `REPSY_ACCOUNT_PASSWORD` environment variables.

---

## CI/CD

| Workflow | Trigger | Purpose |
|---|---|---|
| `ci.yml` | Push / PR → main, master | Build + test (`mvn clean verify`) + upload JaCoCo artifact |
| `build.yml` | Push / PR → main, develop | SonarCloud analysis via `sonar-maven-plugin` |
| `qodana_code_quality.yml` | Push / PR / manual | JetBrains Qodana static analysis + SARIF upload |
