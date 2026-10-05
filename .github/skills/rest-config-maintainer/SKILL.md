---
name: REST and Config Maintainer Skills
description: Consolidated skill set for RestTemplate, Jackson, MapStruct, and properties classes in commons-services.
applies-to:
  - REST and Config Maintainer
---

# REST and Config Maintainer -- Skill Definition

## 1. Project-Specific Patterns

### ClientRestTemplate construction
```java
// Always use BufferingClientHttpRequestFactory so request body can be re-read by logging interceptors
public ClientRestTemplate(MappingJackson2HttpMessageConverter messageConverter) {
    super(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
    List<HttpMessageConverter<?>> converters = new ArrayList<>();
    converters.add(messageConverter);
    this.setMessageConverters(converters);
}
```

### JacksonConfig ObjectMapper bean
```java
@Bean
public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    return mapper;
}
```

### MapStruct mapper declaration
```java
// config = MapperAppConfig.class is MANDATORY
@Mapper(config = MapperAppConfig.class)
public interface MyEntityMapper {
    MyDto toDto(MyEntity entity);
}
```

### ConfigurationProperties class (no Lombok)
```java
@ConfigurationProperties(prefix = "prx.security")
@Component
public class SecurityProperties {
    private String keystorePath;

    // Explicit constructor with comment (no Lombok)
    public SecurityProperties() {
    }

    public String getKeystorePath() { return keystorePath; }
    public void setKeystorePath(String keystorePath) { this.keystorePath = keystorePath; }
}
```

## 2. Naming Conventions
- REST wrapper: `ClientRestTemplate` extends RestTemplate
- Config classes: `JacksonConfig`, `MapperAppConfig`
- Properties: `SecurityProperties` (prefix prx.security), `DiscoveryClientProperties` (prefix security.discovery.client)
- Utility: `PrinterUtil` (conditional debug logging)

## 3. Key Files
```
src/main/java/com/umdc/commons/services/rest/ClientRestTemplate.java
src/main/java/com/umdc/commons/services/config/mapper/JacksonConfig.java
src/main/java/com/umdc/commons/services/config/mapper/MapperAppConfig.java
src/main/java/com/umdc/commons/services/properties/SecurityProperties.java (if exists)
src/main/java/com/umdc/commons/services/properties/DiscoveryClientProperties.java
src/main/java/com/umdc/commons/services/util/PrinterUtil.java
```

## 4. Constraints
- NEVER use Lombok on any class in this area
- NEVER use @Autowired field injection
- All @Mapper interfaces MUST have config = MapperAppConfig.class
- RestTemplate MUST use BufferingClientHttpRequestFactory
- Jackson ObjectMapper MUST have JavaTimeModule registered
- Properties classes MUST have explicit getters/setters

## 5. Checklist
- [ ] ClientRestTemplate uses BufferingClientHttpRequestFactory
- [ ] JacksonConfig registers JavaTimeModule on ObjectMapper
- [ ] All mappers declare config = MapperAppConfig.class
- [ ] All properties classes have explicit no-arg constructor and accessors
- [ ] No Lombok annotations anywhere
- [ ] Unit tests cover all components
- [ ] mvn -DskipITs clean verify passes
