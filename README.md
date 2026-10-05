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

![Java](https://img.shields.io/badge/Java-21-green?logo=java&style=flat-square) ![Maven](https://img.shields.io/badge/Maven-3.8-lightgrey?logo=apachemaven&style=flat-square) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.8-green?logo=springboot&style=flat-square) ![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.1-green?logo=spring&style=flat-square) ![JUnit](https://img.shields.io/badge/JUnit-5.14.1-green?logo=junit&style=flat-square) ![Mockito](https://img.shields.io/badge/Mockito-5.21.0-green?style=flat-square) ![JaCoCo](https://img.shields.io/badge/JaCoCo-0.8.14-green?style=flat-square) ![PMD](https://img.shields.io/badge/PMD-3.28.0-green?style=flat-square)

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
| `CrudService<A,T>` | `com.prx.commons.services` | Generic CRUD contract returning `ResponseEntity`; all methods default to HTTP 501 |
| `LoggingService` / `LoggingServiceImp` | `com.umdc.commons.services.loggers` | HTTP request/response trace logging; enabled via `prx.logging.trace.enabled` |
| `ClientRestTemplate` | `com.umdc.commons.services.rest` | `RestTemplate` wrapper with buffered factory and Jackson converter |
| `ImageApi` | `com.umdc.commons.services.cloudflare.controller` | Spring MVC interface for image REST endpoints (upload, download, delete, exists, reference, list) |
| `ImageService` | `com.umdc.commons.services.cloudflare.service` | Service contract for image save/retrieve/delete operations |
| `CloudflareR2StorageClient` | `com.umdc.commons.services.cloudflare.r2.client` | AWS SDK v2 S3 client pre-configured for Cloudflare R2 |
| `CloudflareR2Properties` | `com.umdc.commons.services.cloudflare.properties` | `@ConfigurationProperties` for `cloudflare.r2.*` settings |
| `SecurityProperties` | `com.prx.commons.services` | Keystore, truststore, and management authenticator config |

---

## Using this library

### Maven coordinates

```xml
<dependency>
    <groupId>com.prx</groupId>
    <artifactId>commons-services</artifactId>
    <version>0.0.2</version>
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

**Requirements:** JDK 21, Maven 3.8+, network access to Maven Central and the Repsy repository.

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

CI runs on a JDK 21 runner. Recommended pipeline:

1. Checkout repository
2. Install JDK 21
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

**Byte Buddy / Mockito + Java 21** — Tests may fail with `"Java 21 (61) not supported"`. Workaround:

```bash
mvn -Dnet.bytebuddy.experimental=true -DskipITs clean test
```

Long-term fix: upgrade `net.bytebuddy` and `org.mockito` to versions with explicit Java 21 support.

---

## Documentation

| Document | Description |
|---|---|
| [docs/image-api-implementation-guide.md](docs/image-api-implementation-guide.md) | How to implement `ImageApi` and `ImageService` in a consuming service |
| [docs/cloudflare-r2-integration.md](docs/cloudflare-r2-integration.md) | Low-level `CloudflareR2StorageClient` configuration and R2 quirks |
| [docs/architecture.md](docs/architecture.md) | Component architecture and how this library fits the PRX platform |
| [docs/configuration.md](docs/configuration.md) | All supported configuration properties |
| [docs/api-reference.md](docs/api-reference.md) | REST API reference for all exposed endpoints |
| CHANGELOG.md | Project changelog and migration notes |

---

## Contributing

When opening pull requests:

- Add or update unit/integration tests for functional changes
- Ensure `mvn -DskipITs clean verify` passes locally with JDK 21
- Update the changelog and relevant docs

---

## License

See the `LICENSE` file in the repository root for license details.

