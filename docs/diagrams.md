# Architecture Diagrams

All diagrams use [Mermaid](https://mermaid.js.org/) syntax and render natively in GitHub, GitLab, and most modern documentation platforms.

---

## 1. Library Module Overview

```mermaid
graph TD
    subgraph lib["commons-services (shared library JAR)"]
        subgraph Core
            CS["CrudService(A,T)\nGeneric CRUD contract"]
            CRT["ClientRestTemplate\nBuffered RestTemplate wrapper"]
        end

        subgraph Logging
            LS["LoggingService\ninterface"]
            LSI["LoggingServiceImp\n@Service"]
            LWC["LoggerWebConfigurer\n@Configuration"]
            LI["LogInterceptor\n@Component · HandlerInterceptor"]
            RqBI["RequestBodyInterceptor\n@ControllerAdvice"]
            RsBI["ResponseBodyInterceptor\n@ControllerAdvice"]
        end

        subgraph Config
            JC["JacksonConfig\n@Configuration · ObjectMapper bean"]
            MC["MapperAppConfig\n@MapperConfig · MapStruct base"]
            PU["PrinterUtil\n@Service · conditional logger"]
        end

        subgraph Properties
            SP["SecurityProperties\nprx.security.*"]
            DP["DiscoveryClientProperties\nsecurity.discovery.client.*"]
        end

        subgraph Cloudflare
            PIA["ProfileImageApi\nREST interface"]
            PIS["ProfileImageService\nservice interface"]
            R2C["CloudflareR2StorageClient\n@Component"]
            R2P["CloudflareR2Properties\ncloudflare.r2.*"]
            POST["PostProfileImageResponse\nrecord"]
            GETR["GetProfileImageReferenceResponse\nrecord"]
        end
    end

    LSI -.->|implements| LS
    LWC -->|registers| LI
    LI -->|calls| LS
    RqBI -->|calls| LS
    RsBI -->|calls| LS
    R2C -->|reads| R2P
    PIA -->|returns| POST
    PIA -->|returns| GETR
```

---

## 2. Spring MVC Request Pipeline

```mermaid
sequenceDiagram
    participant Client
    participant DispatcherServlet
    participant LogInterceptor
    participant RequestBodyInterceptor
    participant Controller
    participant ResponseBodyInterceptor
    participant LoggingServiceImp

    Client->>DispatcherServlet: HTTP Request

    DispatcherServlet->>LogInterceptor: preHandle(request, response, handler)
    alt method is GET / POST / PUT / DELETE
        LogInterceptor->>LoggingServiceImp: displayRequest(request, null)
        Note over LoggingServiceImp: Logs method + URI
    end
    LogInterceptor-->>DispatcherServlet: return true

    DispatcherServlet->>RequestBodyInterceptor: afterBodyRead(body, ...)
    RequestBodyInterceptor->>LoggingServiceImp: displayRequest(request, body)
    Note over LoggingServiceImp: Logs method + URI + body
    RequestBodyInterceptor-->>DispatcherServlet: body unchanged

    DispatcherServlet->>Controller: handle(request)
    Controller-->>DispatcherServlet: ResponseEntity

    DispatcherServlet->>ResponseBodyInterceptor: beforeBodyWrite(body, ...)
    ResponseBodyInterceptor->>LoggingServiceImp: displayResponse(request, response, body)
    Note over LoggingServiceImp: Logs method + headers + body
    ResponseBodyInterceptor-->>DispatcherServlet: body unchanged

    DispatcherServlet-->>Client: HTTP Response
```

---

## 3. Cloudflare R2 — Upload Flow

```mermaid
sequenceDiagram
    participant Client
    participant ProfileImageApi
    participant ProfileImageService
    participant CloudflareR2StorageClient
    participant CloudflareR2

    Client->>ProfileImageApi: POST /application/{applicationId}<br/>Header: session-token<br/>Body: multipart image bytes

    ProfileImageApi->>ProfileImageService: save(token, applicationId, image)

    ProfileImageService->>CloudflareR2StorageClient: uploadImage(bytes, objectKey, contentType)

    alt S3Client not yet initialized
        CloudflareR2StorageClient->>CloudflareR2StorageClient: buildS3Client()<br/>pathStyleAccess=true · chunkedEncoding=false
    end

    CloudflareR2StorageClient->>CloudflareR2: PutObject(bucket, objectKey, bytes)
    CloudflareR2-->>CloudflareR2StorageClient: 200 OK

    CloudflareR2StorageClient-->>ProfileImageService: objectKey
    ProfileImageService-->>ProfileImageApi: ResponseEntity 201 · PostProfileImageResponse
    ProfileImageApi-->>Client: 201 Created { "ref": "profiles/app-id/..." }
```

---

## 4. Cloudflare R2 — Retrieve Reference Flow

```mermaid
sequenceDiagram
    participant Client
    participant ProfileImageApi
    participant ProfileImageService
    participant CloudflareR2StorageClient

    Client->>ProfileImageApi: GET /application/{applicationId}/reference<br/>Header: session-token

    ProfileImageApi->>ProfileImageService: getProfileImageReference(token, applicationId)
    ProfileImageService->>CloudflareR2StorageClient: getPublicUrl(objectKey)

    alt publicUrl is configured
        CloudflareR2StorageClient-->>ProfileImageService: publicUrl + "/" + objectKey
    else publicUrl not set
        CloudflareR2StorageClient-->>ProfileImageService: objectKey (raw)
    end

    ProfileImageService-->>ProfileImageApi: ResponseEntity 200 · GetProfileImageReferenceResponse
    ProfileImageApi-->>Client: 200 OK { "ref": "https://pub-xxx.r2.dev/profiles/..." }
```

---

## 5. Interface-with-Default-Methods Pattern

```mermaid
classDiagram
    class CrudService~A,T~ {
        <<interface>>
        +create(T t) ResponseEntity~T~
        +update(A id, T t) ResponseEntity~T~
        +delete(A id, T t) ResponseEntity~T~
        +find(A id) ResponseEntity~T~
        +list(A id) ResponseEntity~List~T~~
    }
    note for CrudService "All defaults return HTTP 501\nOverride to implement"

    class ProfileImageService {
        <<interface>>
        +save(String token, UUID appId, byte[] image) ResponseEntity
        +getProfileImageReference(String token, UUID appId) ResponseEntity
    }
    note for ProfileImageService "All defaults throw\nUnsupportedOperationException"

    class ProfileImageApi {
        <<interface>>
        +uploadProfileImage(String token, UUID appId, byte[] image) ResponseEntity
        +getProfileImage(String token) ResponseEntity
        +getProfileImageReference(String token, UUID appId) ResponseEntity
        +getService() ProfileImageService
    }
    note for ProfileImageApi "All defaults return HTTP 501\nSpring MVC annotations on methods"

    class ConcreteController {
        +uploadProfileImage(...)
        +getProfileImageReference(...)
    }

    class ConcreteService {
        +save(...)
        +getProfileImageReference(...)
    }

    ConcreteController ..|> ProfileImageApi : implements
    ConcreteService ..|> ProfileImageService : implements
    ProfileImageApi ..> ProfileImageService : delegates to
```

---

## 6. Configuration Properties Map

```mermaid
graph TD
    subgraph yml["application.yml"]
        P1["prx.logging.trace.enabled"]
        P2["cloudflare.r2.*"]
        P3["prx.security.*"]
        P4["security.discovery.client.*"]
        P5["log.debug"]
    end

    P1 -->|"@Value"| LSI["LoggingServiceImp"]
    P2 -->|"@ConfigurationProperties"| R2P["CloudflareR2Properties"]
    P3 -->|"@ConfigurationProperties"| SP["SecurityProperties"]
    P4 -->|"@ConfigurationProperties"| DP["DiscoveryClientProperties"]
    P5 -->|"@Value"| PU["PrinterUtil"]

    R2P -->|"injected into"| R2C["CloudflareR2StorageClient"]

    SP --> KS["keystore\nStoreProperties"]
    SP --> TS["truststore\nStoreProperties"]
    SP --> MA["managementAuthenticator\nManagementAuthenticatorProperties"]

    MA --> MAK["keystore\nStoreProperties"]
    MA --> MAT["truststore\nStoreProperties"]
```

---

## 7. CI/CD Pipeline

```mermaid
graph TD
    PR["Pull Request\nto main / master"]
    PUSH["Push\nto main / master"]
    MANUAL["Manual dispatch"]

    subgraph ci["ci.yml — Build & Test"]
        CI1["Checkout"] --> CI2["Setup JDK 21\nTemurin"]
        CI2 --> CI3["mvn clean verify"]
        CI3 --> CI4["Upload JaCoCo\nreport artifact"]
    end

    subgraph sonar["build.yml — SonarCloud"]
        SQ1["Checkout\nfull history"] --> SQ2["Setup JDK 21\nZulu"]
        SQ2 --> SQ3["mvn verify sonar:sonar"]
    end

    subgraph qodana["qodana_code_quality.yml — Qodana"]
        QD1["Checkout"] --> QD2["Qodana JVM\nCommunity scan"]
        QD2 --> QD3["Upload SARIF\nto CodeQL"]
    end

    PR --> CI1
    PR --> SQ1
    PR --> QD1
    PUSH --> CI1
    PUSH --> SQ1
    PUSH --> QD1
    MANUAL --> QD1
```

---

## 8. Component Interaction — Library vs Consuming Service

```mermaid
graph TD
    subgraph lib["commons-services (library)"]
        PIA["ProfileImageApi\nREST interface"]
        PIS["ProfileImageService\nservice interface"]
        R2C["CloudflareR2StorageClient\n@Component"]
        LS["LoggingService\ninterface"]
        LSI["LoggingServiceImp\n@Service"]
        MC["MapperAppConfig\n@MapperConfig base"]
        CS["CrudService(A,T)\nCRUD interface"]
    end

    subgraph svc["Consuming Microservice"]
        Ctrl["@RestController\nimplements ProfileImageApi"]
        SvcImpl["@Service\nimplements ProfileImageService"]
        Mapper["@Mapper\nconfig = MapperAppConfig"]
        CrudCtrl["@RestController\nimplements CrudService"]
    end

    Ctrl -->|implements| PIA
    SvcImpl -->|implements| PIS
    SvcImpl -->|uses| R2C
    Mapper -->|config| MC
    CrudCtrl -->|implements| CS
    LSI -->|implements| LS
    Ctrl -.->|logs via| LS
```
