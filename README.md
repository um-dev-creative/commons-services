# PRX Commons Services

[![Qodana](https://github.com/um-dev-creative/commons-services/actions/workflows/qodana_code_quality.yml/badge.svg)](https://github.com/um-dev-creative/commons-services/actions/workflows/qodana_code_quality.yml)
[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=umdc-commons-services)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=coverage)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=bugs)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)

## Technologies

[![Java](https://img.shields.io/badge/Java-25%20LTS-blue?logo=java&style=flat-square)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-brightgreen?logo=spring&style=flat-square)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.1.3-brightgreen?logo=spring&style=flat-square)](https://spring.io/projects/spring-cloud)
[![Maven](https://img.shields.io/badge/Maven->=3.8-red?logo=apachemaven&style=flat-square)](https://maven.apache.org/)
[![AWS SDK](https://img.shields.io/badge/AWS%20SDK%20S3-2.54.5-orange?logo=amazonaws&style=flat-square)](https://aws.amazon.com/sdk-for-java/)
[![Cloudflare R2](https://img.shields.io/badge/Cloudflare%20R2-object%20storage-orange?logo=cloudflare&style=flat-square)](https://developers.cloudflare.com/r2/)
[![MapStruct](https://img.shields.io/badge/MapStruct-1.6.3-blue?logo=mapstruct&style=flat-square)](https://mapstruct.org/)
[![PRX Commons](https://img.shields.io/badge/PRX%20Commons-0.0.3-blue?style=flat-square)](https://repo.repsy.io/mvn/lmata/prx)
[![Spring Framework](https://img.shields.io/badge/Spring%20Framework-7.0.8-brightgreen?logo=spring&style=flat-square)](https://spring.io/projects/spring-framework)
[![Jackson 2.x](https://img.shields.io/badge/Jackson%202.x-2.22.3-blue?style=flat-square)](https://github.com/FasterXML/jackson)
[![Jackson 3.x](https://img.shields.io/badge/Jackson%203.x-3.2.3-blue?style=flat-square)](https://github.com/FasterXML/jackson)
[![Logback](https://img.shields.io/badge/Logback-1.6.5-blue?style=flat-square)](https://logback.qos.ch/)
[![Log4j](https://img.shields.io/badge/Log4j-2.26.1-blue?logo=apache&style=flat-square)](https://logging.apache.org/log4j/)
[![Springdoc OpenAPI](https://img.shields.io/badge/Springdoc%20OpenAPI-3.1.0-brightgreen?logo=openapiinitiative&style=flat-square)](https://springdoc.org/)
[![Gson](https://img.shields.io/badge/Gson-2.14.0-blue?style=flat-square)](https://github.com/google/gson)
[![Netty](https://img.shields.io/badge/Netty-4.2.18.Final-blue?style=flat-square)](https://netty.io/)
[![Tomcat Embed](https://img.shields.io/badge/Tomcat%20Embed-11.0.26-orange?logo=apachetomcat&style=flat-square)](https://tomcat.apache.org/)
[![JUnit](https://img.shields.io/badge/JUnit-6.1.3-red?logo=junit&style=flat-square)](https://junit.org/)
[![Mockito](https://img.shields.io/badge/Mockito-5.21.0-red?logo=mockito&style=flat-square)](https://site.mockito.org/)
[![JaCoCo](https://img.shields.io/badge/JaCoCo-0.8.15-green?style=flat-square)](https://www.jacoco.org/)
[![PMD](https://img.shields.io/badge/PMD%20plugin-3.28.0-green?style=flat-square)](https://pmd.github.io/)
[![SonarCloud](https://img.shields.io/badge/SonarCloud-detected-4E9BCF?logo=sonarcloud&style=flat-square)](https://sonarcloud.io/)

---

## What is this?

`commons-services` is a **shared library (JAR)** — not a runnable application. It provides reusable service components, interfaces, and utilities consumed by PRX microservices. It is published to the private Repsy Maven repository at `repo.repsy.io/mvn/lmata/prx`.

### Purpose

Microservices in the PRX platform share a common set of cross-cutting concerns: HTTP request/response logging, REST client configuration, Cloudflare R2 image storage, and Spring Security properties. Rather than duplicating this logic across services, `commons-services` packages these capabilities as a single dependency that any PRX service can pull in and extend.

### Design pattern

All service interfaces use the **default-method stub** pattern: every operation returns HTTP 501 or throws `UnsupportedOperationException` until the consuming service overrides it. This means consumers implement only what they need, with no abstract-method obligation for unused operations.

---

## What's inside

| Component | Package | Description |
|---|---|---|
| `CrudService<A,T>` | `com.umdc.commons.services` | Generic CRUD contract returning `ResponseEntity`; all methods default to HTTP 501 |
| `LoggingService` / `LoggingServiceImp` | `com.umdc.commons.services.loggers` | HTTP request/response trace logging; enabled via `prx.logging.trace.enabled` |
| `ClientRestTemplate` | `com.umdc.commons.services.rest` | `RestTemplate` wrapper with buffered factory and Jackson converter |
| `ImageApi` | `com.umdc.commons.services.cloudflare.controller` | Spring MVC interface for image REST endpoints (upload, download, delete, exists, reference, list) |
| `ImageService` | `com.umdc.commons.services.cloudflare.service` | Service contract for image save/retrieve/delete operations |
| `CloudflareR2StorageClient` | `com.umdc.commons.services.cloudflare.r2.client` | AWS SDK v2 S3 client pre-configured for Cloudflare R2 |
| `CloudflareR2Properties` | `com.umdc.commons.services.cloudflare.properties` | `@ConfigurationProperties` for `cloudflare.r2.*` settings |
| `StoreProperties` / `ManagementAuthenticatorProperties` | `com.umdc.commons.services.cloudflare.properties` | Keystore/truststore and management authenticator config |
| `DiscoveryClientProperties` | `com.umdc.commons.services.properties` | Eureka/discovery client settings |

---

## Using this library

### Maven coordinates

```xml
<dependency>
    <groupId>com.umdc</groupId>
    <artifactId>commons-services</artifactId>
    <version>0.0.3</version>
</dependency>
```

### Repository

The artifact is published to a private Repsy repository. Add it to your `pom.xml` or `settings.xml`:

```xml
<repository>
    <id>repsy-prx</id>
    <url>https://repo.repsy.io/mvn/lmata/prx</url>
</repository>
```

Set the `REPSY_ACCOUNT_USER` and `REPSY_ACCOUNT_PASSWORD` environment variables for authentication.

### Minimum configuration (Cloudflare R2)

If your service uses image storage, add the following to `application.yml`:

```yaml
cloudflare:
  r2:
    account-id: <your-cloudflare-account-id>
    endpoint: https://<account-id>.r2.cloudflarestorage.com
    access-key: <r2-access-key-id>
    secret-key: <r2-secret-access-key>
    bucket-name: <bucket-name>
    public-url: https://<custom-domain-or-r2-dev-url>  # optional
```

For enabling trace logging:

```yaml
prx:
  logging:
    trace:
      enabled: true
```

---

## Building locally

**Requirements:** JDK 25 (LTS), Maven 3.8+, network access to Maven Central and the Repsy repository.

```bash
# Full build: compile, test, PMD check, JaCoCo coverage verification
mvn clean verify

# Skip integration tests (standard local workflow)
mvn -DskipITs clean verify

# Run unit tests only (no coverage enforcement)
mvn test

# If Byte Buddy/Mockito fails with Java compatibility errors
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test
```

### Quality gates

| Gate | Tool | Threshold |
|---|---|---|
| Static analysis | PMD (`ruleset.xml`) | Fails on any priority-5 violation |
| Line coverage | JaCoCo | 80% minimum (BUNDLE) |
| Branch coverage | JaCoCo | 50% minimum (PACKAGE) |

Coverage is excluded from `**/config/*`, `**/loggers/*`, `**/loggers/interceptor/*`, and `**/mapper/*`.

---

## Continuous Integration

CI runs on a JDK 25 runner. Recommended pipeline:

1. Checkout repository
2. Install JDK 25
3. `mvn -DskipITs clean verify` — unit tests + quality gates
4. `mvn -P integration-tests verify` — integration tests (separate job)
5. Dependency CVE scan (Dependabot, Snyk, or equivalent)

---

## Verifying Sonar coverage locally

```bash
# 1. Generate JaCoCo XML report
mvn -DskipITs clean verify

# 2. Run Sonar analysis
mvn sonar:sonar -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=<SONAR_TOKEN>
```

The report is written to `target/site/jacoco/jacoco.xml`. `pom.xml` already sets `sonar.coverage.jacoco.xmlReportPaths` to that path.

---

## Known issues

**Byte Buddy / Mockito + Java 25** — Tests may fail with `"Java 25 (69) is not supported by the current version of Byte Buddy"`. Workaround:

```bash
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test
```

Long-term fix: upgrade `net.bytebuddy` and `org.mockito` to versions with explicit Java 25 support.

---

## Documentation

| Document | Description |
|---|---|
| [docs/image-api-implementation-guide.md](docs/image-api-implementation-guide.md) | How to implement `ImageApi` and `ImageService` in a consuming service |
| [docs/cloudflare-r2-integration.md](docs/cloudflare-r2-integration.md) | Low-level `CloudflareR2StorageClient` configuration and R2 quirks |
| [docs/architecture.md](docs/architecture.md) | Component architecture and how this library fits the PRX platform |
| [docs/configuration.md](docs/configuration.md) | All supported configuration properties |
| [docs/api-reference.md](docs/api-reference.md) | REST API reference for all exposed endpoints |
| [CHANGELOG.md](CHANGELOG.md) | Project changelog and migration notes |

---

## Tech stack and versions

Versions below are taken from `pom.xml` (properties and parent).

| Technology | Version | Source |
|---|--------------:|---|
| Java (language / runtime) | 25 | pom.xml |
| Spring Boot (parent) | 4.1.1 | pom.xml |
| Spring Cloud | 2025.1.3 | pom.xml |
| Spring Core | 7.0.8 | pom.xml |
| Maven (build tool) | >=3.8 | pom.xml |
| PRX Commons (`com.umdc:commons`) | 0.0.3 | pom.xml |
| AWS SDK v2 (S3, used for Cloudflare R2) | 2.54.5 | pom.xml |
| MapStruct | 1.6.3 | pom.xml |
| Jackson 2.x (databind / jsr310) | 2.22.3 | pom.xml |
| Jackson 2.x annotations | 2.22 | pom.xml |
| Jackson 3.x (`tools.jackson`) | 3.2.3 | pom.xml |
| Logback | 1.6.5 | pom.xml |
| Log4j | 2.26.1 | pom.xml |
| Google Gson | 2.14.0 | pom.xml |
| Springdoc OpenAPI | 3.1.0 | pom.xml |
| Netty | 4.2.18.Final | pom.xml |
| Apache Tomcat Embed (provided) | 11.0.26 | pom.xml |
| ASM | 9.8 | pom.xml |
| JUnit Jupiter | 6.1.3 | pom.xml |
| Mockito | 5.21.0 | pom.xml |
| Maven Compiler Plugin | 3.14.1 | pom.xml |
| Maven Surefire Plugin | 3.5.6 | pom.xml |
| Maven Javadoc Plugin | 3.12.0 | pom.xml |
| JaCoCo (Maven plugin) | 0.8.15 | pom.xml |
| PMD (Maven plugin) | 3.28.0 | pom.xml |
| SonarCloud (project properties present) | detected | pom.xml |

Files scanned: `pom.xml`, `.github/workflows/*.yml` (CI uses JDK 25).

---

## Contributing

When opening pull requests:

- Add or update unit/integration tests for functional changes
- Ensure `mvn -DskipITs clean verify` passes locally with JDK 25
- Update the changelog and relevant docs

---

## License

See the `LICENSE` file in the repository root for license details.

