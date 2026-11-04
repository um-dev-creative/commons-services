# Jackson 3 Migration Guide

## User guide (for consumers of the library)

`commons-services` now uses **Jackson 3.x** (`tools.jackson`). Two public types changed.

| Before (Jackson 2.x) | After (Jackson 3.x) |
|---|---|
| `MappingJackson2HttpMessageConverter` | `org.springframework.http.converter.json.JacksonJsonHttpMessageConverter` |
| `com.fasterxml.jackson.databind.ObjectMapper` | `tools.jackson.databind.ObjectMapper` |

### What you must change

1. Inject `JacksonJsonHttpMessageConverter` into subclasses of `ClientRestTemplate`:

```java
@Service
public class MyApiClient extends ClientRestTemplate {
    public MyApiClient(JacksonJsonHttpMessageConverter converter) {
        super(converter);
    }
}
```

2. Change `ObjectMapper` imports from `com.fasterxml.jackson.databind` to `tools.jackson.databind`.
3. Remove any `JavaTimeModule` registration: `java.time` support is built into Jackson 3.
4. Annotations such as `@JsonProperty` keep the package `com.fasterxml.jackson.annotation`.

## Technical guide

### Changes

- `JacksonConfig.objectMapper()` returns `JsonMapper.builder().build()`.
- `ClientRestTemplate` takes a `JacksonJsonHttpMessageConverter` and still restricts it to `application/json`.
- `pom.xml` declares `tools.jackson.core:jackson-databind` (`jackson3.version`) and no longer declares `jackson-datatype-jsr310`.
- The Jackson 2.x BOM pin stays because `jackson-annotations` 2.x is still used by Jackson 3, and transitive libraries such as springdoc still pull in Jackson 2 modules.

```mermaid
flowchart LR
    JC[JacksonConfig] -->|JsonMapper.builder| OM[tools.jackson ObjectMapper]
    JM[JacksonJsonHttpMessageConverter] --> CRT[ClientRestTemplate]
    CRT --> RT[RestTemplate with buffering factory]
    OM -. java.time built in .-> DT[LocalDate / Instant / ...]
```
