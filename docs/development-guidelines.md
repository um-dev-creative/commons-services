# Development Guidelines

Guidelines for contributing to and extending `commons-services`.

---

## Prerequisites

| Tool | Version | Check Command |
|---|---|---|
| JDK | 21 | `java -version` |
| Maven | 3.8+ (3.9+ recommended) | `mvn -v` |

Dependencies resolve from Maven Central and the Repsy private repository (`repo.repsy.io/mvn/lmata/prx`). No additional setup is required for read access.

---

## Build Commands

```bash
# Full build: compile → test → PMD → JaCoCo coverage check
mvn clean verify

# Skip integration tests (recommended for fast local iteration)
mvn -DskipITs clean verify

# Run tests only (no coverage enforcement)
mvn test

# Run a single test class
mvn test -Dtest=CloudflareR2StorageClientTest

# Run a single test method
mvn test -Dtest=LoggingServiceImpUnitTest#displayRequest_logs_with_parameters_and_body

# Generate JaCoCo HTML report (opens at target/site/jacoco/index.html)
mvn verify
```

If Mockito/Byte Buddy fails with a Java class-file version error:
```bash
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test
```

---

## Adding a New Component

### 1. Define an interface with default stubs

Follow the existing pattern: default methods return HTTP 501 or throw `UnsupportedOperationException`.

```java
public interface MyService {
    default ResponseEntity<MyResponse> execute(MyRequest request) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
```

### 2. If the component is a Spring MVC contract

Use the `ProfileImageApi` pattern: annotate the interface with Spring MVC and OpenAPI annotations directly. Keep the interface in `controller/` and the service contract in `service/`.

### 3. Add `@ConfigurationProperties` for externalized config

```java
@Configuration
@ConfigurationProperties(prefix = "my.feature")
public class MyFeatureProperties {
    private String endpoint;
    // getters + setters (no Lombok — use explicit accessors)
}
```

### 4. Write tests before submitting

The JaCoCo gate enforces **80% line coverage** and **50% branch coverage** on `src/main/java`. New code that reduces coverage below these thresholds will fail `mvn verify`.

---

## Code Style

### PMD Rules

The project enforces a custom PMD ruleset defined in `ruleset.xml`. Key rules relevant to new code:

| Rule | Category | Impact |
|---|---|---|
| `AtLeastOneConstructor` | codestyle | Every class needs an explicit constructor |
| `UncommentedEmptyConstructor` | documentation | Empty constructors need a comment |
| `AvoidDuplicateLiterals` | errorprone | Extract repeated string literals to constants |
| `GodClass` | design | Keep classes focused; avoid large omnibus classes |
| `ImmutableField` | design | Fields set only in constructors should be `final` |
| `UseCollectionIsEmpty` | bestpractices | Prefer `.isEmpty()` over `.size() == 0` |
| `UnusedPrivateField` | bestpractices | Remove unused private fields |
| `AvoidReassigningParameters` | bestpractices | Do not reassign method parameters |
| `UseConcurrentHashMap` | multithreading | Prefer `ConcurrentHashMap` in concurrent contexts |

Run PMD locally without running tests:
```bash
mvn pmd:check pmd:cpd-check
```

### General Conventions

- Use **constructor injection** — no field injection (`@Autowired` on fields)
- Use **Java records** for immutable transfer objects (TOs)
- Use **`ConcurrentHashMap`** when building maps from servlet data (see `LoggingServiceImp`)
- Explicit getter/setter methods — no Lombok (not on the classpath)
- SLF4J for logging: `LoggerFactory.getLogger(MyClass.class)` — never `System.out`

---

## Test Conventions

### Test Structure

| Test type | When to use | Example |
|---|---|---|
| Pure unit (`@ExtendWith(MockitoExtension.class)`) | Logic without Spring context | `LoggingServiceImpUnitTest` |
| Spring slice (`@ExtendWith(SpringExtension.class)`) | When Spring bean lifecycle matters | `LoggingServiceImpTest` |
| Direct instantiation | Simple POJOs or interfaces | `CrudServiceTest`, `DiscoveryClientPropertiesTest` |

### Coverage Exclusions

These packages are excluded from JaCoCo and SonarCloud coverage reporting — tests are welcome but not required:
- `**/config/**`
- `**/loggers/**`
- `**/loggers/interceptor/**`
- `**/mapper/**`

All other packages under `src/main/java` are subject to the 80% line / 50% branch thresholds.

### Mocking Conventions

Use `@Mock` and `@InjectMocks` with `MockitoExtension`. For `@Value`-injected fields not reachable via constructor, use `ReflectionTestUtils.setField(instance, "fieldName", value)`.

```java
@ExtendWith(MockitoExtension.class)
class MyServiceTest {
    @Mock
    private MyDependency dependency;
    @InjectMocks
    private MyService service;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "enabledFlag", true);
    }
}
```

---

## Publishing the Library

Publishing requires Repsy credentials:

```bash
export REPSY_ACCOUNT_USER=<username>
export REPSY_ACCOUNT_PASSWORD=<password>
mvn clean deploy
```

Artifacts are published to `https://repo.repsy.io/mvn/lmata/prx`.

---

## Quality Checks

| Gate | Tool | Threshold | Phase |
|---|---|---|---|
| Line coverage | JaCoCo | ≥ 80% (BUNDLE) | `verify` |
| Branch coverage | JaCoCo | ≥ 50% (PACKAGE) | `verify` |
| Static analysis | PMD | No violations at priority ≤ 5 | `test` |
| Duplicate code | PMD CPD | No violations | `test` |
| Code quality | SonarCloud | Configured in CI | CI only |
| Code quality | Qodana | Configured in CI | CI only |

All gates except SonarCloud and Qodana are enforced locally by `mvn verify`. A build that fails any gate will not produce a deployable artifact.

---

## Branching and CI

- Target branch for PRs: `main`
- CI runs on every push to `main` and on all PRs: `mvn -B -V -e clean verify`
- SonarCloud analysis runs on push to `main`/`master` and on PRs
- Qodana runs on push to `main`/`develop`/`releases/*` and on manual dispatch
