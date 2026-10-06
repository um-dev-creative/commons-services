<div align="center">

<img src="docs/images/um-dev-creatives-logo.png" alt="UM Dev Creatives" width="120" />

# 🧰 Commons Services

**Shared Java library (JAR)** — reusable service contracts, HTTP logging, REST client configuration,
Cloudflare R2 image storage, and configuration properties for PRX microservices.

[![Qodana](https://github.com/um-dev-creative/commons-services/actions/workflows/qodana_code_quality.yml/badge.svg)](https://github.com/um-dev-creative/commons-services/actions/workflows/qodana_code_quality.yml)

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-light.svg)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=umdc-commons-services)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)

[![Quality gate status](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=coverage)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Maintainability issues](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=software_quality_maintainability_issues)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Reliability issues](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=software_quality_reliability_issues)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Security issues](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=software_quality_security_issues)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=umdc-commons-services&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)

<br/>

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

</div>

Overview
--------
`commons-services` is a **shared library (JAR)** — not a runnable application. It provides reusable service components, interfaces, and utilities consumed by PRX microservices, and is published to the private Repsy Maven repository at `repo.repsy.io/mvn/lmata/prx`. It packages the cross-cutting concerns every PRX service needs — HTTP request/response trace logging, `RestTemplate` configuration, Cloudflare R2 image storage (AWS SDK v2 S3 client), and Spring configuration properties — so they are not duplicated across services.

All service interfaces use the **default-method stub** pattern: every operation returns HTTP 501 or throws `UnsupportedOperationException` until the consuming service overrides it, so consumers implement only what they need.

Requirements
------------
Minimum requirements to build and use `commons-services` locally:
- Java 25 (JDK, LTS) or a compatible runtime (Amazon Corretto 25 recommended)
- Maven 3.8+
- `REPSY_ACCOUNT_USER` / `REPSY_ACCOUNT_PASSWORD` environment variables, for the private PRX artifacts (`com.umdc:commons`) and for publishing to Repsy
- Network access to Maven Central and the Repsy repository

Using this library
------------------
Maven coordinates:

```xml
<dependency>
    <groupId>com.umdc</groupId>
    <artifactId>commons-services</artifactId>
    <version>0.0.3</version>
</dependency>
```

Repository (add to your `pom.xml` or `settings.xml`):

```xml
<repository>
    <id>repsy-prx</id>
    <url>https://repo.repsy.io/mvn/lmata/prx</url>
</repository>
```

Minimum configuration for Cloudflare R2 image storage (`application.yml`):

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

Enable HTTP trace logging:

```yaml
prx:
  logging:
    trace:
      enabled: true
```

Quick build
-----------
This project uses Maven. From the repository root:

```bash
# Full build: compile, test, PMD check, JaCoCo coverage verification
mvn clean verify

# Skip integration tests (standard local workflow)
mvn -DskipITs clean verify

# Run unit tests only (no coverage enforcement)
mvn test

# Run a single test class
mvn test -Dtest=LoggingServiceImpTest
```

Quality gates (enforced at build time):

| Gate | Tool | Threshold |
|---|---|---|
| Static analysis | PMD (`ruleset.xml`) | Fails on any priority-5 violation |
| Line coverage | JaCoCo | 80% minimum (BUNDLE) |
| Branch coverage | JaCoCo | 50% minimum (PACKAGE) |

Coverage is excluded from `**/config/*`, `**/loggers/*`, `**/loggers/interceptor/*`, and `**/mapper/*`.

Known issues and workarounds
-----------------------------
1. **Byte Buddy / Mockito + Java 25**
   - Symptom: tests fail with `Java 25 (69) is not supported by the current version of Byte Buddy`.
   - Workaround: `mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test`.
   - Long-term fix: upgrade `net.bytebuddy` and `org.mockito` to versions with explicit Java 25 support.

2. **Repsy authentication failures when resolving `com.umdc:commons`**
   - Symptom: `401`/`Could not transfer artifact` during dependency resolution.
   - Workaround: export `REPSY_ACCOUNT_USER` and `REPSY_ACCOUNT_PASSWORD` before running Maven.

3. **Transitive dependency vulnerability warnings from IDE/Dependabot**
   - Workaround: pin the vulnerable artifact via a `<properties>` entry named `<lib>.version` plus a matching override in `<dependencyManagement>` — see `jackson.databind.version`, `jackson3.version`, `logback.version`, `apache.tomcat.version` in `pom.xml`. Verify with `mvn dependency:tree -Dincludes=<groupId>:<artifactId>`.

4. **Sonar coverage thresholds fail locally but not in CI, or vice versa**
   - Workaround: run `mvn -DskipITs clean verify` first so the JaCoCo XML report exists at `target/site/jacoco/jacoco.xml` before invoking `sonar:sonar`.

Continuous Integration
-----------------------
CI runs on GitHub Actions (`.github/workflows/`) with a JDK 25 runner:
- **`ci.yml` / `build.yml`** — `mvn -B -V -e clean verify` (unit tests + PMD + JaCoCo quality gates)
- **`qodana_code_quality.yml`** — JetBrains Qodana static analysis

## How to verify Sonar coverage locally

1) Generate the JaCoCo XML report (runs tests and produces XML/HTML reports):
   ```bash
   mvn -DskipITs clean verify
   ```

2) Confirm the JaCoCo XML report exists at the path `pom.xml` configures:
   ```bash
   test -f target/site/jacoco/jacoco.xml && echo "report present"
   ```

3) Run Sonar analysis locally (requires a Sonar token):
   ```bash
   mvn sonar:sonar -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=<SONAR_TOKEN>
   ```

Notes:
- `pom.xml` sets `sonar.coverage.jacoco.xmlReportPaths` to `target/site/jacoco/jacoco.xml` and scopes `sonar.inclusions`/`sonar.coverage.inclusions` to `src/main/java/**/*.java`, excluding config/logger/mapper code from coverage accounting.

## Documentation

- `CHANGELOG.md` — project changelog and migration notes (Keep a Changelog / SemVer)
- `CLAUDE.md` — architecture, core abstractions, build and quality-gate notes
- [docs/architecture.md](docs/architecture.md) — component architecture and how this library fits the PRX platform
- [docs/components.md](docs/components.md) — component catalogue
- [docs/configuration.md](docs/configuration.md) — all supported configuration properties
- [docs/api-reference.md](docs/api-reference.md) — REST API reference for all exposed endpoints
- [docs/image-api-implementation-guide.md](docs/image-api-implementation-guide.md) — how to implement `ImageApi` and `ImageService` in a consuming service
- [docs/cloudflare-r2-integration.md](docs/cloudflare-r2-integration.md) — `CloudflareR2StorageClient` configuration and R2 quirks
- [docs/logging-subsystem.md](docs/logging-subsystem.md) — logging interceptors and trace logging
- `LICENSE` — proprietary, all rights reserved (see below)

Components
----------
| Component | Package | Purpose |
|---|---|---|
| `CrudService<A,T>` | `com.umdc.commons.services` | Generic CRUD contract returning `ResponseEntity`; all methods default to HTTP 501 |
| `LoggingService` / `LoggingServiceImp` | `com.umdc.commons.services.loggers` | HTTP request/response trace logging; enabled via `prx.logging.trace.enabled` |
| `ClientRestTemplate` | `com.umdc.commons.services.rest` | `RestTemplate` wrapper with buffered factory and Jackson converter |
| `ImageApi` | `com.umdc.commons.services.cloudflare.controller` | Spring MVC interface for image REST endpoints (upload, download, delete, exists, reference, list) |
| `ImageService` | `com.umdc.commons.services.cloudflare.service` | Service contract for image save/retrieve/delete operations |
| `CloudflareR2StorageClient` | `com.umdc.commons.services.cloudflare.r2.client` | AWS SDK v2 S3 client pre-configured for Cloudflare R2 |
| `CloudflareR2Properties` | `com.umdc.commons.services.cloudflare.properties` | `@ConfigurationProperties` for `cloudflare.r2.*` |
| `StoreProperties` / `ManagementAuthenticatorProperties` | `com.umdc.commons.services.cloudflare.properties` | Keystore/truststore and management authenticator config |
| `DiscoveryClientProperties` | `com.umdc.commons.services.properties` | Eureka/discovery client settings |

Tech stack and versions
-----------------------
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

Note: "detected" means the technology is present but there is no single pinned version string to extract.

Files scanned
-------------
- `pom.xml` — project metadata, properties, dependencies, plugin versions
- `.github/workflows/*.yml` — CI pipeline (JDK 25, Qodana)
- `CLAUDE.md` — project architecture and conventions

License
-------
Proprietary and confidential — Copyright (c) 2024-2026 UM Dev Creative. All Rights Reserved. See
`LICENSE` for the full terms; this is not open-source software.

More
----
For architecture and convention details, see `CLAUDE.md`. For questions, reach out to:

<luis.antonio.mata@gmail.com>

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-light.svg)](https://sonarcloud.io/summary/new_code?id=umdc-commons-services)
